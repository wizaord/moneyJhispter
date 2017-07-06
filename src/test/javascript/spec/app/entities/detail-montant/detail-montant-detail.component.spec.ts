/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MoneyJhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DetailMontantDetailComponent } from '../../../../../../main/webapp/app/entities/detail-montant/detail-montant-detail.component';
import { DetailMontantService } from '../../../../../../main/webapp/app/entities/detail-montant/detail-montant.service';
import { DetailMontant } from '../../../../../../main/webapp/app/entities/detail-montant/detail-montant.model';

describe('Component Tests', () => {

    describe('DetailMontant Management Detail Component', () => {
        let comp: DetailMontantDetailComponent;
        let fixture: ComponentFixture<DetailMontantDetailComponent>;
        let service: DetailMontantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneyJhipsterTestModule],
                declarations: [DetailMontantDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DetailMontantService,
                    JhiEventManager
                ]
            }).overrideTemplate(DetailMontantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailMontantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailMontantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DetailMontant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.detailMontant).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
