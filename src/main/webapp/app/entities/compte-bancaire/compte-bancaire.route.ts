import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompteBancaireComponent } from './compte-bancaire.component';
import { CompteBancaireDetailComponent } from './compte-bancaire-detail.component';
import { CompteBancairePopupComponent } from './compte-bancaire-dialog.component';
import { CompteBancaireDeletePopupComponent } from './compte-bancaire-delete-dialog.component';

import { Principal } from '../../shared';

export const compteBancaireRoute: Routes = [
    {
        path: 'compte-bancaire',
        component: CompteBancaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'compte-bancaire/:id',
        component: CompteBancaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compteBancairePopupRoute: Routes = [
    {
        path: 'compte-bancaire-new',
        component: CompteBancairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte-bancaire/:id/edit',
        component: CompteBancairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte-bancaire/:id/delete',
        component: CompteBancaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
