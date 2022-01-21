import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationHebergementDetailComponent } from 'app/entities/reservation-hebergement/reservation-hebergement-detail.component';
import { ReservationHebergement } from 'app/shared/model/reservation-hebergement.model';

describe('Component Tests', () => {
  describe('ReservationHebergement Management Detail Component', () => {
    let comp: ReservationHebergementDetailComponent;
    let fixture: ComponentFixture<ReservationHebergementDetailComponent>;
    const route = ({ data: of({ reservationHebergement: new ReservationHebergement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationHebergementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReservationHebergementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReservationHebergementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reservationHebergement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reservationHebergement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
