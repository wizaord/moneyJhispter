import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {ComptebancaireListComponent} from './comptebancaireList.component';

export const accountsRoute: Routes = [
    {
        path: 'accounts',
        component: ComptebancaireListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'moneyJhipsterApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
