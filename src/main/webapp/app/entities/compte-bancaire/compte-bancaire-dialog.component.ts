import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompteBancaire } from './compte-bancaire.model';
import { CompteBancairePopupService } from './compte-bancaire-popup.service';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire-dialog',
    templateUrl: './compte-bancaire-dialog.component.html'
})
export class CompteBancaireDialogComponent implements OnInit {

    compteBancaire: CompteBancaire;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private compteBancaireService: CompteBancaireService,
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
        if (this.compteBancaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.compteBancaireService.update(this.compteBancaire));
        } else {
            this.subscribeToSaveResponse(
                this.compteBancaireService.create(this.compteBancaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompteBancaire>) {
        result.subscribe((res: CompteBancaire) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompteBancaire) {
        this.eventManager.broadcast({ name: 'compteBancaireListModification', content: 'OK'});
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
    selector: 'jhi-compte-bancaire-popup',
    template: ''
})
export class CompteBancairePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compteBancairePopupService: CompteBancairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.compteBancairePopupService
                    .open(CompteBancaireDialogComponent, params['id']);
            } else {
                this.modalRef = this.compteBancairePopupService
                    .open(CompteBancaireDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
