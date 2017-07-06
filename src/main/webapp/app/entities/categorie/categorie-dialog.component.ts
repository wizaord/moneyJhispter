import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Categorie } from './categorie.model';
import { CategoriePopupService } from './categorie-popup.service';
import { CategorieService } from './categorie.service';

@Component({
    selector: 'jhi-categorie-dialog',
    templateUrl: './categorie-dialog.component.html'
})
export class CategorieDialogComponent implements OnInit {

    categorie: Categorie;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private categorieService: CategorieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.categorie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categorieService.update(this.categorie));
        } else {
            this.subscribeToSaveResponse(
                this.categorieService.create(this.categorie));
        }
    }

    private subscribeToSaveResponse(result: Observable<Categorie>) {
        result.subscribe((res: Categorie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Categorie) {
        this.eventManager.broadcast({ name: 'categorieListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-categorie-popup',
    template: ''
})
export class CategoriePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoriePopupService: CategoriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.categoriePopupService
                    .open(CategorieDialogComponent, params['id']);
            } else {
                this.modalRef = this.categoriePopupService
                    .open(CategorieDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
