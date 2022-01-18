import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTourComponent } from 'app/entities/tarif-tour/tarif-tour.component';
import { TarifTourService } from 'app/entities/tarif-tour/tarif-tour.service';
import { TarifTour } from 'app/shared/model/tarif-tour.model';

describe('Component Tests', () => {
  describe('TarifTour Management Component', () => {
    let comp: TarifTourComponent;
    let fixture: ComponentFixture<TarifTourComponent>;
    let service: TarifTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTourComponent],
      })
        .overrideTemplate(TarifTourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TarifTour(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tarifTours && comp.tarifTours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
