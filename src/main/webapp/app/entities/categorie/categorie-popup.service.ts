import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Categorie } from './categorie.model';
import { CategorieService } from './categorie.service';

@Injectable()
export class CategoriePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private categorieService: CategorieService

    ) {}

    open(component: any, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.categorieService.find(id).subscribe((categorie) => {
                this.categorieModalRef(component, categorie);
            });
        } else {
            return this.categorieModalRef(component, new Categorie());
        }
    }

    categorieModalRef(component: any, categorie: Categorie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.categorie = categorie;
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
