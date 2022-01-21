import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { HotelMediaComponent } from 'app/entities/hotel-media/hotel-media.component';
import { HotelMediaService } from 'app/entities/hotel-media/hotel-media.service';
import { HotelMedia } from 'app/shared/model/hotel-media.model';

describe('Component Tests', () => {
  describe('HotelMedia Management Component', () => {
    let comp: HotelMediaComponent;
    let fixture: ComponentFixture<HotelMediaComponent>;
    let service: HotelMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [HotelMediaComponent],
      })
        .overrideTemplate(HotelMediaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelMediaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HotelMediaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HotelMedia(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hotelMedias && comp.hotelMedias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
