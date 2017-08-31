import {Component, OnInit} from '@angular/core';
import {CompteBancaireService} from '../account.service';
import {CompteBancaire} from '../models/CompteBancaire';
import {Router} from '@angular/router';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'jhi-accounts',
    templateUrl: './comptebancaireList.component.html',
    styles: []
})
export class ComptebancaireListComponent implements OnInit {

    public accounts: CompteBancaire[];
    public soldeToutCompte: number;

    constructor(public compteBancaireSrv: CompteBancaireService, public router: Router) {
        this.accounts = [];
        this.soldeToutCompte = 0;
    }

    ngOnInit() {
        // recuperation de la liste de comptes
        this.compteBancaireSrv.findAll().subscribe((response) => {
            this.accounts = response;
            response.forEach((elt) => this.soldeToutCompte += elt.montantSolde);
        });
    }

    closeAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.closeAccount(compte.id).subscribe((response) => {
            compte.clos = true;
            console.log('Fermeture du compte ' + compte.id);
        })
    }

    openAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.reopenAccount(compte.id).subscribe((response) => {
            compte.clos = false;
            console.log('Ouverture du compte ' + compte.id);
        })
    }

    deleteAccount(compte: CompteBancaire) {
        this.compteBancaireSrv.deleteAccount(compte.id).subscribe((response) => {
            this.accounts.splice(this.accounts.indexOf(compte), 1);
        })
    }
}
