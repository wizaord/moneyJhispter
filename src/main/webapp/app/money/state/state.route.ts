import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
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
