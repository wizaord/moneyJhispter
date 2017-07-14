import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MoneyJhipsterSharedModule} from '../../shared/shared.module';
import {RouterModule} from '@angular/router';

import {
    statesRoute,
    StatesComponent
} from './';

const ACCOUNT_STATES = [
    ...statesRoute
];

@NgModule({
  imports: [
      MoneyJhipsterSharedModule,
      RouterModule.forRoot(ACCOUNT_STATES, { useHash: true })
  ],
    declarations: [
        StatesComponent
    ],
    entryComponents: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StateModule { }
