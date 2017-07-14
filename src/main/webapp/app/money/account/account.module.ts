import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MoneyJhipsterSharedModule} from '../../shared/shared.module';
import {RouterModule} from '@angular/router';

import {
    AccountsComponent,
    accountsRoute
} from './';

const ACCOUNT_STATES = [
    ...accountsRoute
];

@NgModule({
  imports: [
      MoneyJhipsterSharedModule,
      RouterModule.forRoot(ACCOUNT_STATES, { useHash: true })
  ],
    declarations: [
        AccountsComponent
    ],
    entryComponents: [
        AccountsComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountModule { }
