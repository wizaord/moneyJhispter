import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { DebitCredit } from './debit-credit.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DebitCreditService {

    private resourceUrl = 'api/debit-credits';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(debitCredit: DebitCredit): Observable<DebitCredit> {
        const copy = this.convert(debitCredit);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(debitCredit: DebitCredit): Observable<DebitCredit> {
        const copy = this.convert(debitCredit);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DebitCredit> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateEnregistrement = this.dateUtils
            .convertDateTimeFromServer(entity.dateEnregistrement);
        entity.datePointage = this.dateUtils
            .convertDateTimeFromServer(entity.datePointage);
    }

    private convert(debitCredit: DebitCredit): DebitCredit {
        const copy: DebitCredit = Object.assign({}, debitCredit);

        copy.dateEnregistrement = this.dateUtils.toDate(debitCredit.dateEnregistrement);

        copy.datePointage = this.dateUtils.toDate(debitCredit.datePointage);
        return copy;
    }
}
