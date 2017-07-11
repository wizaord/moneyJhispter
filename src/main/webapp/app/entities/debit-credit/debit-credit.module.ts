import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../../shared';
import {
    DebitCreditService,
    DebitCreditPopupService,
    DebitCreditComponent,
    DebitCreditDetailComponent,
    DebitCreditDialogComponent,
    DebitCreditPopupComponent,
    DebitCreditDeletePopupComponent,
    DebitCreditDeleteDialogComponent,
    debitCreditRoute,
    debitCreditPopupRoute,
    DebitCreditResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...debitCreditRoute,
    ...debitCreditPopupRoute,
];

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DebitCreditComponent,
        DebitCreditDetailComponent,
        DebitCreditDialogComponent,
        DebitCreditDeleteDialogComponent,
        DebitCreditPopupComponent,
        DebitCreditDeletePopupComponent,
    ],
    entryComponents: [
        DebitCreditComponent,
        DebitCreditDialogComponent,
        DebitCreditPopupComponent,
        DebitCreditDeleteDialogComponent,
        DebitCreditDeletePopupComponent,
    ],
    providers: [
        DebitCreditService,
        DebitCreditPopupService,
        DebitCreditResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterDebitCreditModule {}
