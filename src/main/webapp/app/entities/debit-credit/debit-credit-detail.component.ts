import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DebitCredit } from './debit-credit.model';
import { DebitCreditService } from './debit-credit.service';

@Component({
    selector: 'jhi-debit-credit-detail',
    templateUrl: './debit-credit-detail.component.html'
})
export class DebitCreditDetailComponent implements OnInit, OnDestroy {

    debitCredit: DebitCredit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private debitCreditService: DebitCreditService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDebitCredits();
    }

    load(id) {
        this.debitCreditService.find(id).subscribe((debitCredit) => {
            this.debitCredit = debitCredit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDebitCredits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'debitCreditListModification',
            (response) => this.load(this.debitCredit.id)
        );
    }
}
