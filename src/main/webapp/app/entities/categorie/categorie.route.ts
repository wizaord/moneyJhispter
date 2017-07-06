import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CategorieComponent } from './categorie.component';
import { CategorieDetailComponent } from './categorie-detail.component';
import { CategoriePopupComponent } from './categorie-dialog.component';
import { CategorieDeletePopupComponent } from './categorie-delete-dialog.component';

import { Principal } from '../../shared';

export const categorieRoute: Routes = [
    {
        path: 'categorie',
        component: CategorieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'categorie/:id',
        component: CategorieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoriePopupRoute: Routes = [
    {
        path: 'categorie-new',
        component: CategoriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie/:id/edit',
        component: CategoriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie/:id/delete',
        component: CategorieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
