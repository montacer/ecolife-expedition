import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationHebergementComponent } from 'app/entities/reservation-hebergement/reservation-hebergement.component';
import { ReservationHebergementService } from 'app/entities/reservation-hebergement/reservation-hebergement.service';
import { ReservationHebergement } from 'app/shared/model/reservation-hebergement.model';

describe('Component Tests', () => {
  describe('ReservationHebergement Management Component', () => {
    let comp: ReservationHebergementComponent;
    let fixture: ComponentFixture<ReservationHebergementComponent>;
    let service: ReservationHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationHebergementComponent],
      })
        .overrideTemplate(ReservationHebergementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationHebergementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationHebergementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReservationHebergement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reservationHebergements && comp.reservationHebergements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
