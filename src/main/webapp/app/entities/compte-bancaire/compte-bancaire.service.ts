import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CompteBancaire } from './compte-bancaire.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompteBancaireService {

    private resourceUrl = 'api/compte-bancaires';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(compteBancaire: CompteBancaire): Observable<CompteBancaire> {
        const copy = this.convert(compteBancaire);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(compteBancaire: CompteBancaire): Observable<CompteBancaire> {
        const copy = this.convert(compteBancaire);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CompteBancaire> {
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
        entity.dateOuverture = this.dateUtils
            .convertDateTimeFromServer(entity.dateOuverture);
        entity.dateFermeture = this.dateUtils
            .convertDateTimeFromServer(entity.dateFermeture);
    }

    private convert(compteBancaire: CompteBancaire): CompteBancaire {
        const copy: CompteBancaire = Object.assign({}, compteBancaire);

        copy.dateOuverture = this.dateUtils.toDate(compteBancaire.dateOuverture);

        copy.dateFermeture = this.dateUtils.toDate(compteBancaire.dateFermeture);
        return copy;
    }
}
