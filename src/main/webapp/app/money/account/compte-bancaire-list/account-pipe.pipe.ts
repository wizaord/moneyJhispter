import {Pipe, PipeTransform} from '@angular/core';
import {CompteBancaire} from '../models/CompteBancaire';

@Pipe({
    name: 'filterAccountClos',
    pure: false
})
export class AccountPipePipe implements PipeTransform {

    transform(accounts: CompteBancaire[], isClos: boolean): CompteBancaire[] {
        return accounts.filter((account) => account.clos === isClos);
    }

}
