import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {CompteBancaire} from './models/CompteBancaire';
import {Http, Response} from '@angular/http';

@Injectable()
export class CompteBancaireService {

    private resourceUrl = 'api/users/accounts';

    constructor(private http: Http) {
    }

    findAll(): Observable<CompteBancaire[]> {
        return this.http.get(`${this.resourceUrl}/`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    closeAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.resourceUrl}/${accountId}/close`, '');
    }

    reopenAccount(accountId: number): Observable<Response> {
        return this.http.put(`${this.resourceUrl}/${accountId}/reopen`, '');
    }

    deleteAccount(accountId: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${accountId}`);
    }

}
