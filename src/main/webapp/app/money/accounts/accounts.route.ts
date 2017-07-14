import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Principal } from '../../shared';
import {AccountsComponent} from './accounts.component';

export const accountsRoute: Routes = [
    {
        path: 'accounts',
        component: AccountsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
