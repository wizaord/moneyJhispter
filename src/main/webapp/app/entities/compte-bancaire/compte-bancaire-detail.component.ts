import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompteBancaire } from './compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire-detail',
    templateUrl: './compte-bancaire-detail.component.html'
})
export class CompteBancaireDetailComponent implements OnInit, OnDestroy {

    compteBancaire: CompteBancaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private compteBancaireService: CompteBancaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompteBancaires();
    }

    load(id) {
        this.compteBancaireService.find(id).subscribe((compteBancaire) => {
            this.compteBancaire = compteBancaire;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompteBancaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'compteBancaireListModification',
            (response) => this.load(this.compteBancaire.id)
        );
    }
}
