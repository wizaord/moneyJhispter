import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { DebitCredit } from './debit-credit.model';
import { DebitCreditService } from './debit-credit.service';

@Injectable()
export class DebitCreditPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private debitCreditService: DebitCreditService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.debitCreditService.find(id).subscribe((debitCredit) => {
                debitCredit.dateEnregistrement = this.datePipe
                    .transform(debitCredit.dateEnregistrement, 'yyyy-MM-ddThh:mm');
                debitCredit.datePointage = this.datePipe
                    .transform(debitCredit.datePointage, 'yyyy-MM-ddThh:mm');
                this.debitCreditModalRef(component, debitCredit);
            });
        } else {
            return this.debitCreditModalRef(component, new DebitCredit());
        }
    }

    debitCreditModalRef(component: Component, debitCredit: DebitCredit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.debitCredit = debitCredit;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
