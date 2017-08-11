import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'jhi-compte-bancaire-details',
    templateUrl: './compte-bancaire-details.component.html',
    styles: []
})
export class CompteBancaireDetailsComponent implements OnInit {

    private accountIds = [];

    constructor(private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.accountIds = [];
        // check if this page is called for a specific account or not
        if (this.route.snapshot.paramMap.has('id')) {
            // calling for a specific account
            const id = this.route.snapshot.paramMap.get('id');
            this.accountIds.push(id);
        } else {
            // calling for a multiple account
            const ids = this.route.snapshot.paramMap.get('ids');
            ids.split('##').forEach((accountId) => this.accountIds.push(accountId))
        }

        console.log(`Calling page with the followings IDs`);
        console.log(JSON.stringify(this.accountIds));
    }

}
