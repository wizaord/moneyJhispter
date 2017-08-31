import {Routes} from '@angular/router';
import {statesRoute} from './state/state.route';
import {accountsRoute} from './account/accounts.route';

const MONEY_ROUTES = [
    ...statesRoute,
    ...accountsRoute
];

export const moneyState: Routes = [{
    path: 'money',
    children: MONEY_ROUTES
}];
