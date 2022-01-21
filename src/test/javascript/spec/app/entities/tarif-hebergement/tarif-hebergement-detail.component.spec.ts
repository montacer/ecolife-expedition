import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergementDetailComponent } from 'app/entities/tarif-hebergement/tarif-hebergement-detail.component';
import { TarifHebergement } from 'app/shared/model/tarif-hebergement.model';

describe('Component Tests', () => {
  describe('TarifHebergement Management Detail Component', () => {
    let comp: TarifHebergementDetailComponent;
    let fixture: ComponentFixture<TarifHebergementDetailComponent>;
    const route = ({ data: of({ tarifHebergement: new TarifHebergement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TarifHebergementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifHebergementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifHebergement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifHebergement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
