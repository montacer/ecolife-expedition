import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTransfertDetailComponent } from 'app/entities/reservation-transfert/reservation-transfert-detail.component';
import { ReservationTransfert } from 'app/shared/model/reservation-transfert.model';

describe('Component Tests', () => {
  describe('ReservationTransfert Management Detail Component', () => {
    let comp: ReservationTransfertDetailComponent;
    let fixture: ComponentFixture<ReservationTransfertDetailComponent>;
    const route = ({ data: of({ reservationTransfert: new ReservationTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReservationTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReservationTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reservationTransfert on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reservationTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
