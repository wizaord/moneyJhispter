import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../shared';
import { AccountModule } from './account/account.module';
import { StateModule } from './state/state.module';
import {moneyState} from './money.route';

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(moneyState, { useHash: true }),
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
