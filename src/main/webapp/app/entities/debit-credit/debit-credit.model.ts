import { BaseEntity } from './../../shared';

export class DebitCredit implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public dateEnregistrement?: any,
        public isPointe?: boolean,
        public datePointage?: any,
        public montantTotal?: number,
        public libelleBanque?: string,
        public details?: BaseEntity[],
        public compteRattache?: BaseEntity,
    ) {
        this.isPointe = false;
    }
}
