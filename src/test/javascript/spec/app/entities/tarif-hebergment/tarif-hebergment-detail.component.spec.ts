import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergmentDetailComponent } from 'app/entities/tarif-hebergment/tarif-hebergment-detail.component';
import { TarifHebergment } from 'app/shared/model/tarif-hebergment.model';

describe('Component Tests', () => {
  describe('TarifHebergment Management Detail Component', () => {
    let comp: TarifHebergmentDetailComponent;
    let fixture: ComponentFixture<TarifHebergmentDetailComponent>;
    const route = ({ data: of({ tarifHebergment: new TarifHebergment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TarifHebergmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifHebergmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifHebergment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifHebergment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
