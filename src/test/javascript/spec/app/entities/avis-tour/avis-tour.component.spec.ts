import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourComponent } from 'app/entities/avis-tour/avis-tour.component';
import { AvisTourService } from 'app/entities/avis-tour/avis-tour.service';
import { AvisTour } from 'app/shared/model/avis-tour.model';

describe('Component Tests', () => {
  describe('AvisTour Management Component', () => {
    let comp: AvisTourComponent;
    let fixture: ComponentFixture<AvisTourComponent>;
    let service: AvisTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourComponent],
      })
        .overrideTemplate(AvisTourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvisTour(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avisTours && comp.avisTours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
