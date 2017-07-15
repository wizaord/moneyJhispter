import { Component, OnInit } from '@angular/core';
import {CompteBancaireService} from './account.service';
import {CompteBancaire} from './models/CompteBancaire';

@Component({
  selector: 'jhi-accounts',
  templateUrl: './comptebancaireList.component.html',
  styles: []
})
export class ComptebancaireListComponent implements OnInit {

    public accounts: CompteBancaire[];

  constructor(public compteBancaireSrv: CompteBancaireService) { }

  ngOnInit() {
      // recuperation de la liste de comptes
      console.log('Loaded all customer accounts')
      this.compteBancaireSrv.getAllUserAccount().subscribe((response) => {
          this.accounts = response;
          console.log(JSON.stringify(this.accounts));
      });

  }

}
