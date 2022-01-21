import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTransfertDetailComponent } from 'app/entities/tarif-transfert/tarif-transfert-detail.component';
import { TarifTransfert } from 'app/shared/model/tarif-transfert.model';

describe('Component Tests', () => {
  describe('TarifTransfert Management Detail Component', () => {
    let comp: TarifTransfertDetailComponent;
    let fixture: ComponentFixture<TarifTransfertDetailComponent>;
    const route = ({ data: of({ tarifTransfert: new TarifTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TarifTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifTransfert on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
