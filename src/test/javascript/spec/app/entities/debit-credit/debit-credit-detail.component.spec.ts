/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MoneyJhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DebitCreditDetailComponent } from '../../../../../../main/webapp/app/entities/debit-credit/debit-credit-detail.component';
import { DebitCreditService } from '../../../../../../main/webapp/app/entities/debit-credit/debit-credit.service';
import { DebitCredit } from '../../../../../../main/webapp/app/entities/debit-credit/debit-credit.model';

describe('Component Tests', () => {

    describe('DebitCredit Management Detail Component', () => {
        let comp: DebitCreditDetailComponent;
        let fixture: ComponentFixture<DebitCreditDetailComponent>;
        let service: DebitCreditService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneyJhipsterTestModule],
                declarations: [DebitCreditDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DebitCreditService,
                    JhiEventManager
                ]
            }).overrideTemplate(DebitCreditDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DebitCreditDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DebitCreditService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DebitCredit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.debitCredit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
