import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {ModalDismissReasons, NgbDateStruct, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DebitCredit, DetailMontant} from '../account.model';
import {CompteBancaireService} from '../account.service';

@Component({
    selector: '[jhi-compte-bancaire-td]',
    templateUrl: './compte-bancaire-detail-modal.component.html',
    encapsulation: ViewEncapsulation.None,
    styles: [`
        .dark-modal .modal-content {
            background-color: #292b2c;
            color: white;
        }

        .dark-modal .close {
            color: white;
        }
    `]
})
export class CompteBancaireDetailModalComponent implements OnInit {

    @Input('debitCredit')
    public debitCredit: DebitCredit;

    public dateTransaction: NgbDateStruct;

    @ViewChild('content') modalContent;

    @Output()
    onUpdate = new EventEmitter<DebitCredit>();

    constructor(private modalService: NgbModal, private accountService: CompteBancaireService) {
    }

    ngOnInit() {
    }

    open() {
        console.log('Opening modal for debitCredit : ' + this.debitCredit.id);

        // converting the date transaction
        this.dateTransaction = this.dateToNgbDateStruct(this.debitCredit.dateTransaction);

        // opening the modal page
        this.modalService.open(this.modalContent).result.then((result) => {
            console.log(`Closed with: ${result}`);
            this.debitCredit.dateTransaction = this.ngbDateStructToDate(this.dateTransaction);
            this.onUpdate.emit(this.debitCredit);
        }, (reason) => {
            console.log(`Dismissed ${this.getDismissReason(reason)}`);
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

    getCategorieName(debitCredit: DebitCredit): string {
        if (debitCredit.details.length === 0) {
            return '';
        }
        if (debitCredit.details.length === 1) {
            const detail: DetailMontant = debitCredit.details[0];
            if (detail.virementInterne === true) {
                return 'virement interne : ' + this.accountService.getAccountName(debitCredit.compteId);
            }
            return debitCredit.details[0].categorieName;
        }
        return 'ventilation';
    }

    get accountName(): String {
        return this.accountService.getAccountName(this.debitCredit.compteId);
    }

    get dateTransactionCorrect(): Date {
        const currentDate: Date = new Date(this.debitCredit.dateTransaction);
        currentDate.setDate(currentDate.getDate() - 1);
        return currentDate;
    }

    private dateToNgbDateStruct(myDate: Date): NgbDateStruct {
        const currentDate: Date = new Date(this.debitCredit.dateTransaction);
        return { day: currentDate.getDate() - 1, month: currentDate.getMonth() + 1, year: currentDate.getFullYear() };
    }

    private ngbDateStructToDate(myDate: NgbDateStruct): Date {
        return new Date(myDate.year, myDate.month - 1, myDate.day + 1);
    }

    get datePointageCheck(): boolean {
        return this.debitCredit.datePointage !== null;
    }

    set datePointageCheck(input: boolean){
        this.debitCredit.datePointage = (input) ? new Date() : null;
    }
}
