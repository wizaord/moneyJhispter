import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {CompteBancaire} from './models/CompteBancaire';
import {Http, Response} from '@angular/http';
import {DebitCreditSearch} from './index';
import {DebitCredit} from './account.model';

@Injectable()
export class CompteBancaireService {
    private compteBancaireUrl = 'api/users/accounts';
    private debitCreditUrl = '/api/users/debitcredit';

    public userCompteBancaire: CompteBancaire[] = [];

    constructor(private http: Http) {
    }

    findAll(): Observable<CompteBancaire[]> {
        if (this.userCompteBancaire.length !== 0) {
            return Observable.of(this.userCompteBancaire);
        }
        return this.http.get(`${this.compteBancaireUrl}/`)
            .map((res: Response) => {
            const jsonResponse = res.json();
            this.userCompteBancaire = res.json();
            return jsonResponse;
        });
    }

    getAccountName(accountId: number): String {
        // console.log('Looking for the number ' + accountId);
        // console.log('In => ' + JSON.stringify(this.userCompteBancaire));
        if (this.userCompteBancaire.length === 0) {
            return '';
        }
        return this.userCompteBancaire.filter((account) => account.id === accountId)[0].libelle;
    }

    closeAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.compteBancaireUrl}/${accountId}/close`, '')
            .map((res: Response) => {
                this.userCompteBancaire = [];
                return res;
            });
    }

    reopenAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.compteBancaireUrl}/${accountId}/reopen`, '')
            .map((res: Response) => {
                this.userCompteBancaire = [];
                return res;
            });
    }

    deleteAccount(accountId: number): Observable<Response> {
        return this.http.delete(`${this.compteBancaireUrl}/${accountId}`)
            .map((res: Response) => {
                this.userCompteBancaire = [];
                return res;
            });
    }

    getAccountsDetails(accountIds: number[], beginDate: Date, endDate: Date): Observable<DebitCredit[]> {
        const criteria: DebitCreditSearch = {
            compteIds: accountIds,
            beginDate,
            endDate
        };

        return this.http.post(`${this.debitCreditUrl}/search`, criteria).map((res: Response) => {
            let jsonResponse: DebitCredit[] = [];
            if (res.text().length !== 0) {
                jsonResponse = res.json();
            }
            return jsonResponse;
        })
    }

}
