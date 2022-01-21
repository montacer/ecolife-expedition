import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTourComponent } from 'app/entities/reservation-tour/reservation-tour.component';
import { ReservationTourService } from 'app/entities/reservation-tour/reservation-tour.service';
import { ReservationTour } from 'app/shared/model/reservation-tour.model';

describe('Component Tests', () => {
  describe('ReservationTour Management Component', () => {
    let comp: ReservationTourComponent;
    let fixture: ComponentFixture<ReservationTourComponent>;
    let service: ReservationTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTourComponent],
      })
        .overrideTemplate(ReservationTourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationTourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationTourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReservationTour(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reservationTours && comp.reservationTours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
