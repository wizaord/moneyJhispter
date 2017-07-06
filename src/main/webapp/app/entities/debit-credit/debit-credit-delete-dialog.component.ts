import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DebitCredit } from './debit-credit.model';
import { DebitCreditPopupService } from './debit-credit-popup.service';
import { DebitCreditService } from './debit-credit.service';

@Component({
    selector: 'jhi-debit-credit-delete-dialog',
    templateUrl: './debit-credit-delete-dialog.component.html'
})
export class DebitCreditDeleteDialogComponent {

    debitCredit: DebitCredit;

    constructor(
        private debitCreditService: DebitCreditService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.debitCreditService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'debitCreditListModification',
                content: 'Deleted an debitCredit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-debit-credit-delete-popup',
    template: ''
})
export class DebitCreditDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private debitCreditPopupService: DebitCreditPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.debitCreditPopupService
                .open(DebitCreditDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
