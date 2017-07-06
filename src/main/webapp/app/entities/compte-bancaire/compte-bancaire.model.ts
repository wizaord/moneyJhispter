import { BaseEntity } from './../../shared';

export class CompteBancaire implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public numeroCompte?: string,
        public montantSolde?: number,
        public dateOuverture?: any,
        public dateFermeture?: any,
        public isClos?: boolean,
        public isDeleted?: boolean,
        public proprietaire?: number,
        public debitscredits?: BaseEntity[],
    ) {
        this.isClos = false;
        this.isDeleted = false;
    }
}
