import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Principal } from '../../shared';
import {StatesComponent} from './states.component';

export const statesRoute: Routes = [
    {
        path: 'states',
        component: StatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
