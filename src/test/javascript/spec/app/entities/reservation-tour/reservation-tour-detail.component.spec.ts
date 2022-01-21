import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTourDetailComponent } from 'app/entities/reservation-tour/reservation-tour-detail.component';
import { ReservationTour } from 'app/shared/model/reservation-tour.model';

describe('Component Tests', () => {
  describe('ReservationTour Management Detail Component', () => {
    let comp: ReservationTourDetailComponent;
    let fixture: ComponentFixture<ReservationTourDetailComponent>;
    const route = ({ data: of({ reservationTour: new ReservationTour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReservationTourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReservationTourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reservationTour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reservationTour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
