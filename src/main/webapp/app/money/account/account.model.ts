export class DebitCreditSearch {

    constructor(public compteIds: number[],
                public beginDate: Date,
                public endDate: Date,
                public libelleMatch?: string,
                public categorieName?: string) {
    }
}

export class DebitCredit {
    constructor(
        public id: number,
        public dateTransaction: Date,
        public datePointage: Date,
        public libellePerso: string,
        public libelleBanque: string,
        public compteId: number,
        public montantTotal: number,
        public details?: DetailMontant[],
    ) {}
}

export class DetailMontant {
    constructor(
        public id: number,
        public montant: number,
        public categorieId: number,
        public categorieName: string,
        public virementInterneCompteId?: number,
        public virementInterne?: number,
    ) {}
}
