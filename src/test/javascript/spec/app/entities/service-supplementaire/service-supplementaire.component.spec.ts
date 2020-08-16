import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ServiceSupplementaireComponent } from 'app/entities/service-supplementaire/service-supplementaire.component';
import { ServiceSupplementaireService } from 'app/entities/service-supplementaire/service-supplementaire.service';
import { ServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';

describe('Component Tests', () => {
  describe('ServiceSupplementaire Management Component', () => {
    let comp: ServiceSupplementaireComponent;
    let fixture: ComponentFixture<ServiceSupplementaireComponent>;
    let service: ServiceSupplementaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ServiceSupplementaireComponent],
      })
        .overrideTemplate(ServiceSupplementaireComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceSupplementaireComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupplementaireService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceSupplementaire(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceSupplementaires && comp.serviceSupplementaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
