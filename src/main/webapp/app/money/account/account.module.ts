import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MoneyJhipsterSharedModule} from '../../shared/shared.module';

import {CompteBancaireDetailsComponent, ComptebancaireListComponent, CompteBancaireService} from './';
import {CompteBancaireDetailModalComponent} from './compte-bancaire-detail-modal/compte-bancaire-detail-modal.component';
import {AccountPipePipe} from './compte-bancaire-list/account-pipe.pipe';
import {RouterModule} from '@angular/router';

@NgModule({
    imports: [
        MoneyJhipsterSharedModule,
        RouterModule
    ],
    declarations: [
        ComptebancaireListComponent,
        CompteBancaireDetailsComponent,
        CompteBancaireDetailModalComponent,
        AccountPipePipe
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
