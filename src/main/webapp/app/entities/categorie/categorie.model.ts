import { BaseEntity } from './../../shared';

export class Categorie implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public isCredit?: boolean,
        public abonnementRegulier?: boolean,
        public categoriePere?: number,
    ) {
        this.isCredit = false;
        this.abonnementRegulier = false;
    }
}
