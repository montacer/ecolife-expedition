import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTourDetailComponent } from 'app/entities/tarif-tour/tarif-tour-detail.component';
import { TarifTour } from 'app/shared/model/tarif-tour.model';

describe('Component Tests', () => {
  describe('TarifTour Management Detail Component', () => {
    let comp: TarifTourDetailComponent;
    let fixture: ComponentFixture<TarifTourDetailComponent>;
    const route = ({ data: of({ tarifTour: new TarifTour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TarifTourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifTourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifTour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifTour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
