import {Component, OnInit} from '@angular/core';
import {CompteBancaireService} from './account.service';
import {CompteBancaire} from './models/CompteBancaire';

@Component({
    selector: 'jhi-accounts',
    templateUrl: './comptebancaireList.component.html',
    styles: []
})
export class ComptebancaireListComponent implements OnInit {

    public accounts: CompteBancaire[];
    public accountsInactif: CompteBancaire[];

    constructor(public compteBancaireSrv: CompteBancaireService) {
        this.accounts = [];
        this.accountsInactif = [];
    }

    ngOnInit() {
        // recuperation de la liste de comptes
        this.compteBancaireSrv.findAll().subscribe((response) => {
            this.accounts = response.filter((acc) => acc.clos === false);
            this.accountsInactif = response.filter((acc) => acc.clos === true);
        });
    }

    closeAccount(compte: CompteBancaire) {
        // TODO : faire la fermeture du compte
        this.accounts.splice(this.accounts.indexOf(compte), 1);
        this.accountsInactif.push(compte);
        console.log('Fermeture du compte ' + compte.id);
    }

    openAccount(compte: CompteBancaire) {
        // TODO : faire l'ouverture du compte
        this.accountsInactif.splice(this.accounts.indexOf(compte), 1);
        this.accounts.push(compte);
        console.log('Ouverture du compte ' + compte.id);
    }

    deleteAccount(compte: CompteBancaire) {
        // TODO : faire la suppression du compte
        this.accountsInactif.splice(this.accounts.indexOf(compte), 1);
    }
}
