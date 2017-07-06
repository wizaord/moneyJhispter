import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DebitCredit } from './debit-credit.model';
import { DebitCreditService } from './debit-credit.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-debit-credit',
    templateUrl: './debit-credit.component.html'
})
export class DebitCreditComponent implements OnInit, OnDestroy {
debitCredits: DebitCredit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private debitCreditService: DebitCreditService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.debitCreditService.query().subscribe(
            (res: ResponseWrapper) => {
                this.debitCredits = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDebitCredits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DebitCredit) {
        return item.id;
    }
    registerChangeInDebitCredits() {
        this.eventSubscriber = this.eventManager.subscribe('debitCreditListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
