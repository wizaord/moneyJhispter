export class CompteBancaire {
    constructor(
        public id?: number,
        public libelle?: string,
        public numeroCompte?: string,
        public montantSolde?: number,
        public dateOuverture?: any,
        public dateFermeture?: any,
        public isClos?: boolean,
        public isDeleted?: boolean,
        public proprietaire?: number
    ) {
        this.isClos = false;
        this.isDeleted = false;
    }
}
