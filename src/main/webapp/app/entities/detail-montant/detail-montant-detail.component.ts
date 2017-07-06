import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DetailMontant } from './detail-montant.model';
import { DetailMontantService } from './detail-montant.service';

@Component({
    selector: 'jhi-detail-montant-detail',
    templateUrl: './detail-montant-detail.component.html'
})
export class DetailMontantDetailComponent implements OnInit, OnDestroy {

    detailMontant: DetailMontant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detailMontantService: DetailMontantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetailMontants();
    }

    load(id) {
        this.detailMontantService.find(id).subscribe((detailMontant) => {
            this.detailMontant = detailMontant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetailMontants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detailMontantListModification',
            (response) => this.load(this.detailMontant.id)
        );
    }
}
