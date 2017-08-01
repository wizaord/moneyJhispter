export class CompteBancaire {
    constructor(
        public id?: number,
        public libelle?: string,
        public numeroCompte?: string,
        public montantSolde?: number,
        public dateOuverture?: any,
        public dateFermeture?: any,
        public clos?: boolean,
        public deleted?: boolean,
        public proprietaire?: number,
        public lastTransaction?: any,
    ) {
        this.clos = false;
        this.deleted = false;
    }
}
