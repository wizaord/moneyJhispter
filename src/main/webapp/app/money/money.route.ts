import { Routes } from '@angular/router';

import {
    accountsRoute,
    statesRoute
} from './';

const MONEY_ROUTES = [
    accountsRoute,
    statesRoute
];

export const moneyState: Routes = [{
    path: '',
    children: MONEY_ROUTES
}];
