import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../shared';
import { AccountModule } from './account/account.module';
import { StateModule } from './state/state.module';

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        AccountModule,
        StateModule
    ],
    declarations: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterMoneyModule {}
