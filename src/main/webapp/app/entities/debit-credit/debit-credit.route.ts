import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DebitCreditComponent } from './debit-credit.component';
import { DebitCreditDetailComponent } from './debit-credit-detail.component';
import { DebitCreditPopupComponent } from './debit-credit-dialog.component';
import { DebitCreditDeletePopupComponent } from './debit-credit-delete-dialog.component';

import { Principal } from '../../shared';

export const debitCreditRoute: Routes = [
    {
        path: 'debit-credit',
        component: DebitCreditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.debitCredit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'debit-credit/:id',
        component: DebitCreditDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.debitCredit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const debitCreditPopupRoute: Routes = [
    {
        path: 'debit-credit-new',
        component: DebitCreditPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.debitCredit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'debit-credit/:id/edit',
        component: DebitCreditPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.debitCredit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'debit-credit/:id/delete',
        component: DebitCreditDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.debitCredit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
