import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {CompteBancaire} from './models/CompteBancaire';
import {Http, Response} from '@angular/http';

@Injectable()
export class CompteBancaireService {

    private resourceUrl = 'api/users/accounts/';

    constructor(private http: Http) {
    }

    findAll(): Observable<CompteBancaire[]> {
        return this.http.get(this.resourceUrl).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

}
