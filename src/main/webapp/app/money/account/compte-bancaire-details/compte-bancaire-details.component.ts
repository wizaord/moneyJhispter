import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CompteBancaireService} from '../account.service';
import {DebitCredit} from '../account.model';

@Component({
    selector: 'jhi-compte-bancaire-details',
    templateUrl: './compte-bancaire-details.component.html',
    styles: []
})
export class CompteBancaireDetailsComponent implements OnInit {

    public accountIds = [];
    public pageDate: Date;
    public debitCredits: DebitCredit[];

    constructor(private route: ActivatedRoute,
                private router: Router,
                private accountService: CompteBancaireService) {
    }

    ngOnInit() {
        const tempDate: Date = new Date();
        this.pageDate = new Date(Date.UTC(tempDate.getFullYear(), tempDate.getMonth(), 1, 0, 0, 0));

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

        // check if the month has been specified
        if (this.route.snapshot.paramMap.has('pageDate')) {
            const inputDate = this.route.snapshot.paramMap.get('pageDate');
            const inputYear = inputDate.substring(0, inputDate.indexOf('-'));
            const inputMonth = inputDate.substr(inputDate.indexOf('-') + 1);
            this.pageDate.setFullYear(parseInt(inputYear, 10), parseInt(inputMonth, 10));
        }

        this.loadPageContent();
    }

    private loadPageContent(): void {
        console.log(`Loading account ids details :`);
        console.log(JSON.stringify(this.accountIds));
        console.log(`Loading content for month ${this.getCurrentMonthPage}`)

        this.debitCredits = [];

        // // calling get detais from service
        const nextMonthPage = new Date(this.pageDate);
        nextMonthPage.setMonth(nextMonthPage.getMonth() + 1);
        this.accountService.getAccountsDetails(this.accountIds, this.pageDate, nextMonthPage).subscribe((response) => {
            this.debitCredits = response;
            console.log('Nb item retrieve : ' + this.debitCredits.length);
        });
    }

    onClickPreviousMonth() {
        this.pageDate.setMonth(this.pageDate.getMonth() - 1);
        this.refreshPageWithParameter();
    }

    onClickNextMonth() {
        this.pageDate.setMonth(this.pageDate.getMonth() + 1);
        this.refreshPageWithParameter();
    }

    private refreshPageWithParameter(): void {
        this.router.navigate(['/accountDetails', {id : this.accountIds.join('#'), pageDate : this.getCurrentMonthPage}]);
        this.loadPageContent();
    }

    get getCurrentMonthPage(): string {
        return this.formatDate(this.pageDate);
    }

    get getPreviousMonthDate(): string {
        const newDate = new Date(this.pageDate);
        newDate.setMonth(newDate.getMonth() - 1);
        return this.formatDate(newDate);
    }

    get getNextMonthDate(): string {
        const newDate = new Date(this.pageDate);
        newDate.setMonth(newDate.getMonth() + 1);
        return this.formatDate(newDate);
    }

    private formatDate(inputDate: Date): string {
        return `${inputDate.getFullYear()}-${inputDate.getUTCMonth()}`
    }
}
