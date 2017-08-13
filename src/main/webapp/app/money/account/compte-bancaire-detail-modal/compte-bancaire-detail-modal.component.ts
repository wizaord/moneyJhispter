import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DebitCredit, DetailMontant} from '../account.model';
import {CompteBancaireService} from '../account.service';

@Component({
    selector: 'jhi-compte-bancaire-detail-modal',
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

    @Output()
    onUpdate = new EventEmitter<DebitCredit>();

    constructor(private modalService: NgbModal, private accountService: CompteBancaireService) {
    }

    ngOnInit() {
    }

    open(content) {
        console.log('Opening modal for debitCredit : ' + this.debitCredit.id);
        this.modalService.open(content).result.then((result) => {
            console.log(`Closed with: ${result}`);
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
            return  `with: ${reason}`;
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

}
