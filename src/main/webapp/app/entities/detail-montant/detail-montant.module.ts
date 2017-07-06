import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../../shared';
import {
    DetailMontantService,
    DetailMontantPopupService,
    DetailMontantComponent,
    DetailMontantDetailComponent,
    DetailMontantDialogComponent,
    DetailMontantPopupComponent,
    DetailMontantDeletePopupComponent,
    DetailMontantDeleteDialogComponent,
    detailMontantRoute,
    detailMontantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...detailMontantRoute,
    ...detailMontantPopupRoute,
];

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DetailMontantComponent,
        DetailMontantDetailComponent,
        DetailMontantDialogComponent,
        DetailMontantDeleteDialogComponent,
        DetailMontantPopupComponent,
        DetailMontantDeletePopupComponent,
    ],
    entryComponents: [
        DetailMontantComponent,
        DetailMontantDialogComponent,
        DetailMontantPopupComponent,
        DetailMontantDeleteDialogComponent,
        DetailMontantDeletePopupComponent,
    ],
    providers: [
        DetailMontantService,
        DetailMontantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterDetailMontantModule {}
