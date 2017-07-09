import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyJhipsterSharedModule } from '../../shared';
import {
    CompteBancaireService,
    CompteBancairePopupService,
    CompteBancaireComponent,
    CompteBancaireDetailComponent,
    CompteBancaireDialogComponent,
    CompteBancairePopupComponent,
    CompteBancaireDeletePopupComponent,
    CompteBancaireDeleteDialogComponent,
    compteBancaireRoute,
    compteBancairePopupRoute,
    CompteBancaireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...compteBancaireRoute,
    ...compteBancairePopupRoute,
];

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompteBancaireComponent,
        CompteBancaireDetailComponent,
        CompteBancaireDialogComponent,
        CompteBancaireDeleteDialogComponent,
        CompteBancairePopupComponent,
        CompteBancaireDeletePopupComponent,
    ],
    entryComponents: [
        CompteBancaireComponent,
        CompteBancaireDialogComponent,
        CompteBancairePopupComponent,
        CompteBancaireDeleteDialogComponent,
        CompteBancaireDeletePopupComponent,
    ],
    providers: [
        CompteBancaireService,
        CompteBancairePopupService,
        CompteBancaireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneyJhipsterCompteBancaireModule {}
