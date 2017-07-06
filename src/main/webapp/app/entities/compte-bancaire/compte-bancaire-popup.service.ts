import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CompteBancaire } from './compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';

@Injectable()
export class CompteBancairePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private compteBancaireService: CompteBancaireService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.compteBancaireService.find(id).subscribe((compteBancaire) => {
                compteBancaire.dateOuverture = this.datePipe
                    .transform(compteBancaire.dateOuverture, 'yyyy-MM-ddThh:mm');
                compteBancaire.dateFermeture = this.datePipe
                    .transform(compteBancaire.dateFermeture, 'yyyy-MM-ddThh:mm');
                this.compteBancaireModalRef(component, compteBancaire);
            });
        } else {
            return this.compteBancaireModalRef(component, new CompteBancaire());
        }
    }

    compteBancaireModalRef(component: Component, compteBancaire: CompteBancaire): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.compteBancaire = compteBancaire;
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
