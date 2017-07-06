import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DetailMontantComponent } from './detail-montant.component';
import { DetailMontantDetailComponent } from './detail-montant-detail.component';
import { DetailMontantPopupComponent } from './detail-montant-dialog.component';
import { DetailMontantDeletePopupComponent } from './detail-montant-delete-dialog.component';

import { Principal } from '../../shared';

export const detailMontantRoute: Routes = [
    {
        path: 'detail-montant',
        component: DetailMontantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.detailMontant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'detail-montant/:id',
        component: DetailMontantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.detailMontant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailMontantPopupRoute: Routes = [
    {
        path: 'detail-montant-new',
        component: DetailMontantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.detailMontant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-montant/:id/edit',
        component: DetailMontantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.detailMontant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-montant/:id/delete',
        component: DetailMontantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.detailMontant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
