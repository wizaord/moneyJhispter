/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MoneyJhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompteBancaireDetailComponent } from '../../../../../../main/webapp/app/entities/compte-bancaire/compte-bancaire-detail.component';
import { CompteBancaireService } from '../../../../../../main/webapp/app/entities/compte-bancaire/compte-bancaire.service';
import { CompteBancaire } from '../../../../../../main/webapp/app/entities/compte-bancaire/compte-bancaire.model';

describe('Component Tests', () => {

    describe('CompteBancaire Management Detail Component', () => {
        let comp: CompteBancaireDetailComponent;
        let fixture: ComponentFixture<CompteBancaireDetailComponent>;
        let service: CompteBancaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneyJhipsterTestModule],
                declarations: [CompteBancaireDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompteBancaireService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompteBancaireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteBancaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteBancaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompteBancaire(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.compteBancaire).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
