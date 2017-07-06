import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetailMontant } from './detail-montant.model';
import { DetailMontantPopupService } from './detail-montant-popup.service';
import { DetailMontantService } from './detail-montant.service';
import { Categorie, CategorieService } from '../categorie';
import { DebitCredit, DebitCreditService } from '../debit-credit';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-detail-montant-dialog',
    templateUrl: './detail-montant-dialog.component.html'
})
export class DetailMontantDialogComponent implements OnInit {

    detailMontant: DetailMontant;
    authorities: any[];
    isSaving: boolean;

    categories: Categorie[];

    debitcredits: DebitCredit[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private detailMontantService: DetailMontantService,
        private categorieService: CategorieService,
        private debitCreditService: DebitCreditService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categorieService.query()
            .subscribe((res: ResponseWrapper) => { this.categories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.debitCreditService.query()
            .subscribe((res: ResponseWrapper) => { this.debitcredits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detailMontant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detailMontantService.update(this.detailMontant));
        } else {
            this.subscribeToSaveResponse(
                this.detailMontantService.create(this.detailMontant));
        }
    }

    private subscribeToSaveResponse(result: Observable<DetailMontant>) {
        result.subscribe((res: DetailMontant) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DetailMontant) {
        this.eventManager.broadcast({ name: 'detailMontantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }

    trackDebitCreditById(index: number, item: DebitCredit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-detail-montant-popup',
    template: ''
})
export class DetailMontantPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailMontantPopupService: DetailMontantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.detailMontantPopupService
                    .open(DetailMontantDialogComponent, params['id']);
            } else {
                this.modalRef = this.detailMontantPopupService
                    .open(DetailMontantDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
