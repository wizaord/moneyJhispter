import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CompteBancaireService} from '../account.service';
import {DebitCredit, DetailMontant} from '../account.model';

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
        this.loadPageContent();
    }

    onClickNextMonth() {
        this.pageDate.setMonth(this.pageDate.getMonth() + 1);
        this.loadPageContent();
    }

    get getPreviousMonthDate(): Date {
        const newDate = new Date(this.pageDate);
        newDate.setMonth(newDate.getMonth() - 1);
        return newDate;
    }

    get getNextMonthDate(): Date {
        const newDate = new Date(this.pageDate);
        newDate.setMonth(newDate.getMonth() + 1);
        return newDate;
    }

    updateDebitCredit(debitCredit: DebitCredit) {
        console.log('Update this element : ' + debitCredit.id);
        console.log(JSON.stringify(debitCredit));
    }
}
