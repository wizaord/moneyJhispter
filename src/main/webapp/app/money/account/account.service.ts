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

    constructor(private http: Http) {
    }

    findAll(): Observable<CompteBancaire[]> {
        return this.http.get(`${this.compteBancaireUrl}/`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    closeAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.compteBancaireUrl}/${accountId}/close`, '');
    }

    reopenAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.compteBancaireUrl}/${accountId}/reopen`, '');
    }

    deleteAccount(accountId: number): Observable<Response> {
        return this.http.delete(`${this.compteBancaireUrl}/${accountId}`);
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
