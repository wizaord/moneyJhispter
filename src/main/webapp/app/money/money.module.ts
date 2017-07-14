import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../shared';

import {
    moneyState,
    AccountsComponent,
    StatesComponent
} from './';

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(moneyState, { useHash: true })
    ],
    declarations: [
        AccountsComponent,
        StatesComponent],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterMoneyModule {}
