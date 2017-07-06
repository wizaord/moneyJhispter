import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetailMontant } from './detail-montant.model';
import { DetailMontantPopupService } from './detail-montant-popup.service';
import { DetailMontantService } from './detail-montant.service';

@Component({
    selector: 'jhi-detail-montant-delete-dialog',
    templateUrl: './detail-montant-delete-dialog.component.html'
})
export class DetailMontantDeleteDialogComponent {

    detailMontant: DetailMontant;

    constructor(
        private detailMontantService: DetailMontantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailMontantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detailMontantListModification',
                content: 'Deleted an detailMontant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detail-montant-delete-popup',
    template: ''
})
export class DetailMontantDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailMontantPopupService: DetailMontantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.detailMontantPopupService
                .open(DetailMontantDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
