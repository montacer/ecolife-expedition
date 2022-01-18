import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifServiceSupplementaireDetailComponent } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire-detail.component';
import { TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

describe('Component Tests', () => {
  describe('TarifServiceSupplementaire Management Detail Component', () => {
    let comp: TarifServiceSupplementaireDetailComponent;
    let fixture: ComponentFixture<TarifServiceSupplementaireDetailComponent>;
    const route = ({ data: of({ tarifServiceSupplementaire: new TarifServiceSupplementaire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifServiceSupplementaireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TarifServiceSupplementaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifServiceSupplementaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifServiceSupplementaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifServiceSupplementaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
