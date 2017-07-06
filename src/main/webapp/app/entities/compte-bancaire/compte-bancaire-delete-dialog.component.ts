import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompteBancaire } from './compte-bancaire.model';
import { CompteBancairePopupService } from './compte-bancaire-popup.service';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire-delete-dialog',
    templateUrl: './compte-bancaire-delete-dialog.component.html'
})
export class CompteBancaireDeleteDialogComponent {

    compteBancaire: CompteBancaire;

    constructor(
        private compteBancaireService: CompteBancaireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compteBancaireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'compteBancaireListModification',
                content: 'Deleted an compteBancaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compte-bancaire-delete-popup',
    template: ''
})
export class CompteBancaireDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compteBancairePopupService: CompteBancairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.compteBancairePopupService
                .open(CompteBancaireDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
