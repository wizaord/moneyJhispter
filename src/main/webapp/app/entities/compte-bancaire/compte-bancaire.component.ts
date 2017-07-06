import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CompteBancaire } from './compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-compte-bancaire',
    templateUrl: './compte-bancaire.component.html'
})
export class CompteBancaireComponent implements OnInit, OnDestroy {
compteBancaires: CompteBancaire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private compteBancaireService: CompteBancaireService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.compteBancaireService.query().subscribe(
            (res: ResponseWrapper) => {
                this.compteBancaires = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompteBancaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompteBancaire) {
        return item.id;
    }
    registerChangeInCompteBancaires() {
        this.eventSubscriber = this.eventManager.subscribe('compteBancaireListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
