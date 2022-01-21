import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementComponent } from 'app/entities/avis-hebergement/avis-hebergement.component';
import { AvisHebergementService } from 'app/entities/avis-hebergement/avis-hebergement.service';
import { AvisHebergement } from 'app/shared/model/avis-hebergement.model';

describe('Component Tests', () => {
  describe('AvisHebergement Management Component', () => {
    let comp: AvisHebergementComponent;
    let fixture: ComponentFixture<AvisHebergementComponent>;
    let service: AvisHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementComponent],
      })
        .overrideTemplate(AvisHebergementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisHebergementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisHebergementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvisHebergement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avisHebergements && comp.avisHebergements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
