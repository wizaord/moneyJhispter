import {Component, OnInit} from '@angular/core';
import {CompteBancaireService} from '../account.service';
import {CompteBancaire} from '../models/CompteBancaire';
import {Router} from '@angular/router';

@Component({
    selector: 'jhi-accounts',
    templateUrl: './comptebancaireList.component.html',
    styles: []
})
export class ComptebancaireListComponent implements OnInit {

    public accounts: CompteBancaire[];
    public accountsInactif: CompteBancaire[];
    public soldeToutCompte: number;

    constructor(public compteBancaireSrv: CompteBancaireService, public router: Router) {
        this.accounts = [];
        this.accountsInactif = [];
        this.soldeToutCompte = 0;
    }

    ngOnInit() {
        // recuperation de la liste de comptes
        this.compteBancaireSrv.findAll().subscribe((response) => {
            this.accounts = response.filter((acc) => acc.clos === false);
            this.accountsInactif = response.filter((acc) => acc.clos === true);

            response.forEach((elt) => this.soldeToutCompte += elt.montantSolde);
        });
    }

    closeAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.closeAccount(compte.id).subscribe((response) => {
            this.accounts.splice(this.accounts.indexOf(compte), 1);
            this.accountsInactif.push(compte);
            console.log('Fermeture du compte ' + compte.id);
        })
    }

    openAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.reopenAccount(compte.id).subscribe((response) => {
            this.accountsInactif.splice(this.accountsInactif.indexOf(compte), 1);
            this.accounts.push(compte);
            console.log('Ouverture du compte ' + compte.id);
        })
    }

    deleteAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.deleteAccount(compte.id).subscribe((response) => {
            this.accountsInactif.splice(this.accountsInactif.indexOf(compte), 1);
        })
    }

    onClickAccount(compte: CompteBancaire) {
        this.router.navigate(['/accountDetails', {id : compte.id}]);
    }
}
