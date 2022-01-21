import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTransfertComponent } from 'app/entities/reservation-transfert/reservation-transfert.component';
import { ReservationTransfertService } from 'app/entities/reservation-transfert/reservation-transfert.service';
import { ReservationTransfert } from 'app/shared/model/reservation-transfert.model';

describe('Component Tests', () => {
  describe('ReservationTransfert Management Component', () => {
    let comp: ReservationTransfertComponent;
    let fixture: ComponentFixture<ReservationTransfertComponent>;
    let service: ReservationTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTransfertComponent],
      })
        .overrideTemplate(ReservationTransfertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationTransfertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationTransfertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReservationTransfert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reservationTransferts && comp.reservationTransferts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
