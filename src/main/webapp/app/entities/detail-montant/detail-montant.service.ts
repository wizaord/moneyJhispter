import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DetailMontant } from './detail-montant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetailMontantService {

    private resourceUrl = 'api/detail-montants';

    constructor(private http: Http) { }

    create(detailMontant: DetailMontant): Observable<DetailMontant> {
        const copy = this.convert(detailMontant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(detailMontant: DetailMontant): Observable<DetailMontant> {
        const copy = this.convert(detailMontant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DetailMontant> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(detailMontant: DetailMontant): DetailMontant {
        const copy: DetailMontant = Object.assign({}, detailMontant);
        return copy;
    }
}
