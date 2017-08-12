import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MoneyJhipsterCategorieModule } from './categorie/categorie.module';
import { MoneyJhipsterCompteBancaireModule } from './compte-bancaire/compte-bancaire.module';
import { MoneyJhipsterDebitCreditModule } from './debit-credit/debit-credit.module';
import { MoneyJhipsterDetailMontantModule } from './detail-montant/detail-montant.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MoneyJhipsterCategorieModule,
        MoneyJhipsterCompteBancaireModule,
        MoneyJhipsterDebitCreditModule,
        MoneyJhipsterDetailMontantModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterEntityModule {}
