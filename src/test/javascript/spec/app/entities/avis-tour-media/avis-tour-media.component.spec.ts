import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourMediaComponent } from 'app/entities/avis-tour-media/avis-tour-media.component';
import { AvisTourMediaService } from 'app/entities/avis-tour-media/avis-tour-media.service';
import { AvisTourMedia } from 'app/shared/model/avis-tour-media.model';

describe('Component Tests', () => {
  describe('AvisTourMedia Management Component', () => {
    let comp: AvisTourMediaComponent;
    let fixture: ComponentFixture<AvisTourMediaComponent>;
    let service: AvisTourMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourMediaComponent],
      })
        .overrideTemplate(AvisTourMediaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTourMediaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTourMediaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvisTourMedia(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avisTourMedias && comp.avisTourMedias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
