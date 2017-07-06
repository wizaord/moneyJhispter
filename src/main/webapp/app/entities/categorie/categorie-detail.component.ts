import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Categorie } from './categorie.model';
import { CategorieService } from './categorie.service';

@Component({
    selector: 'jhi-categorie-detail',
    templateUrl: './categorie-detail.component.html'
})
export class CategorieDetailComponent implements OnInit, OnDestroy {

    categorie: Categorie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categorieService: CategorieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategories();
    }

    load(id) {
        this.categorieService.find(id).subscribe((categorie) => {
            this.categorie = categorie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categorieListModification',
            (response) => this.load(this.categorie.id)
        );
    }
}
