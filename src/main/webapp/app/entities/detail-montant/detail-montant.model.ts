import { BaseEntity } from './../../shared';

export class DetailMontant implements BaseEntity {
    constructor(
        public id?: number,
        public montant?: number,
        public virementInterneCompteId?: number,
        public categorie?: BaseEntity,
        public debitCreditAssocie?: BaseEntity,
    ) {
    }
}
