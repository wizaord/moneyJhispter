import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Categorie } from './categorie.model';
import { CategoriePopupService } from './categorie-popup.service';
import { CategorieService } from './categorie.service';

@Component({
    selector: 'jhi-categorie-delete-dialog',
    templateUrl: './categorie-delete-dialog.component.html'
})
export class CategorieDeleteDialogComponent {

    categorie: Categorie;

    constructor(
        private categorieService: CategorieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categorieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categorieListModification',
                content: 'Deleted an categorie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-categorie-delete-popup',
    template: ''
})
export class CategorieDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoriePopupService: CategoriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.categoriePopupService
                .open(CategorieDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
