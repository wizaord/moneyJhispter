import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MoneyJhipsterSharedModule} from '../../shared/shared.module';

import {StatesComponent} from './';

@NgModule({
    imports: [
        MoneyJhipsterSharedModule
    ],
    declarations: [
        StatesComponent
    ],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StateModule {
}
