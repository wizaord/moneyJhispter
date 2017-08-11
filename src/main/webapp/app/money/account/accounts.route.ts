import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {ComptebancaireListComponent} from './compte-bancaire-list/comptebancaireList.component';
import {CompteBancaireDetailsComponent} from './compte-bancaire-details/compte-bancaire-details.component';

export const accountsRoute: Routes = [
    {
        path: 'accounts',
        component: ComptebancaireListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.accounts.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accountDetails',
        component: CompteBancaireDetailsComponent,
        canActivate: [UserRouteAccessService]
    }
];
