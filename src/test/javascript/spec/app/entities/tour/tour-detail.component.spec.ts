import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TourDetailComponent } from 'app/entities/tour/tour-detail.component';
import { Tour } from 'app/shared/model/tour.model';

describe('Component Tests', () => {
  describe('Tour Management Detail Component', () => {
    let comp: TourDetailComponent;
    let fixture: ComponentFixture<TourDetailComponent>;
    const route = ({ data: of({ tour: new Tour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
