import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DetailMontant } from './detail-montant.model';
import { DetailMontantService } from './detail-montant.service';

@Injectable()
export class DetailMontantPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detailMontantService: DetailMontantService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.detailMontantService.find(id).subscribe((detailMontant) => {
                this.detailMontantModalRef(component, detailMontant);
            });
        } else {
            return this.detailMontantModalRef(component, new DetailMontant());
        }
    }

    detailMontantModalRef(component: Component, detailMontant: DetailMontant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detailMontant = detailMontant;
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
