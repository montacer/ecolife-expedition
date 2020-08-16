import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { HotelDetailComponent } from 'app/entities/hotel/hotel-detail.component';
import { Hotel } from 'app/shared/model/hotel.model';

describe('Component Tests', () => {
  describe('Hotel Management Detail Component', () => {
    let comp: HotelDetailComponent;
    let fixture: ComponentFixture<HotelDetailComponent>;
    const route = ({ data: of({ hotel: new Hotel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [HotelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HotelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HotelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hotel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hotel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
