import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TourMediaComponent } from 'app/entities/tour-media/tour-media.component';
import { TourMediaService } from 'app/entities/tour-media/tour-media.service';
import { TourMedia } from 'app/shared/model/tour-media.model';

describe('Component Tests', () => {
  describe('TourMedia Management Component', () => {
    let comp: TourMediaComponent;
    let fixture: ComponentFixture<TourMediaComponent>;
    let service: TourMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TourMediaComponent],
      })
        .overrideTemplate(TourMediaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourMediaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourMediaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TourMedia(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tourMedias && comp.tourMedias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
