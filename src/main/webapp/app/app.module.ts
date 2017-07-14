import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { MoneyJhipsterSharedModule, UserRouteAccessService } from './shared';
import { MoneyJhipsterHomeModule } from './home/home.module';
import { MoneyJhipsterAdminModule } from './admin/admin.module';
import { MoneyJhipsterAccountModule } from './account/account.module';
import { MoneyJhipsterEntityModule } from './entities/entity.module';
import { MoneyJhipsterMoneyModule } from './money/money.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        MoneyJhipsterSharedModule,
        MoneyJhipsterHomeModule,
        MoneyJhipsterAdminModule,
        MoneyJhipsterAccountModule,
        MoneyJhipsterEntityModule,
        MoneyJhipsterMoneyModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class MoneyJhipsterAppModule {}
