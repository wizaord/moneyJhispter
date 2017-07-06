import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DebitCredit } from './debit-credit.model';
import { DebitCreditPopupService } from './debit-credit-popup.service';
import { DebitCreditService } from './debit-credit.service';
import { CompteBancaire, CompteBancaireService } from '../compte-bancaire';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-debit-credit-dialog',
    templateUrl: './debit-credit-dialog.component.html'
})
export class DebitCreditDialogComponent implements OnInit {

    debitCredit: DebitCredit;
    authorities: any[];
    isSaving: boolean;

    comptebancaires: CompteBancaire[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private debitCreditService: DebitCreditService,
        private compteBancaireService: CompteBancaireService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.compteBancaireService.query()
            .subscribe((res: ResponseWrapper) => { this.comptebancaires = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.debitCredit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.debitCreditService.update(this.debitCredit));
        } else {
            this.subscribeToSaveResponse(
                this.debitCreditService.create(this.debitCredit));
        }
    }

    private subscribeToSaveResponse(result: Observable<DebitCredit>) {
        result.subscribe((res: DebitCredit) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DebitCredit) {
        this.eventManager.broadcast({ name: 'debitCreditListModification', content: 'OK'});
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

    trackCompteBancaireById(index: number, item: CompteBancaire) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-debit-credit-popup',
    template: ''
})
export class DebitCreditPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private debitCreditPopupService: DebitCreditPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.debitCreditPopupService
                    .open(DebitCreditDialogComponent, params['id']);
            } else {
                this.modalRef = this.debitCreditPopupService
                    .open(DebitCreditDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
