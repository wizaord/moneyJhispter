import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MoneyJhipsterSharedModule} from '../../shared/shared.module';
import {RouterModule} from '@angular/router';

import {accountsRoute, CompteBancaireDetailsComponent, ComptebancaireListComponent, CompteBancaireService} from './';

const ACCOUNT_STATES = [
    ...accountsRoute
];

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(ACCOUNT_STATES, {useHash: true, enableTracing: true})
    ],
    declarations: [
        ComptebancaireListComponent,
        CompteBancaireDetailsComponent
    ],
    entryComponents: [
        ComptebancaireListComponent
    ],
    providers: [
        CompteBancaireService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountModule {
}
