import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementMediaComponent } from 'app/entities/avis-hebergement-media/avis-hebergement-media.component';
import { AvisHebergementMediaService } from 'app/entities/avis-hebergement-media/avis-hebergement-media.service';
import { AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';

describe('Component Tests', () => {
  describe('AvisHebergementMedia Management Component', () => {
    let comp: AvisHebergementMediaComponent;
    let fixture: ComponentFixture<AvisHebergementMediaComponent>;
    let service: AvisHebergementMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementMediaComponent],
      })
        .overrideTemplate(AvisHebergementMediaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisHebergementMediaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisHebergementMediaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvisHebergementMedia(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avisHebergementMedias && comp.avisHebergementMedias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
