import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {CompteBancaire} from './models/CompteBancaire';
import {Http} from '@angular/http';

@Injectable()
export class CompteBancaireService {

    private accountFixtif: CompteBancaire[] = [{
        id: 1,
        dateFermeture: null,
        dateOuverture: null,
        isClos: false,
        isDeleted: false,
        libelle: 'my account',
        montantSolde: 2345,
        proprietaire: 123,
        numeroCompte: 'AZERTYUIO'
    }, {
        id: 2,
        dateFermeture: null,
        dateOuverture: null,
        isClos: false,
        isDeleted: false,
        libelle: 'my account',
        montantSolde: 2345,
        proprietaire: 123,
        numeroCompte: 'AZERTYUIO'
    }, {
        id: 3,
        dateFermeture: null,
        dateOuverture: null,
        isClos: false,
        isDeleted: false,
        libelle: 'my account',
        montantSolde: -10,
        proprietaire: 123,
        numeroCompte: 'AZERTYUIO'
    }];

  constructor(private http: Http) { }

    /**
     * Get all user accounts
     * @return {any}
     */
  public getAllUserAccount(): Observable<CompteBancaire[]> {
    return Observable.of(this.accountFixtif).delay(9);
  }

}
