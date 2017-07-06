import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DetailMontant } from './detail-montant.model';
import { DetailMontantService } from './detail-montant.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-detail-montant',
    templateUrl: './detail-montant.component.html'
})
export class DetailMontantComponent implements OnInit, OnDestroy {
detailMontants: DetailMontant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private detailMontantService: DetailMontantService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.detailMontantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.detailMontants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDetailMontants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DetailMontant) {
        return item.id;
    }
    registerChangeInDetailMontants() {
        this.eventSubscriber = this.eventManager.subscribe('detailMontantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
