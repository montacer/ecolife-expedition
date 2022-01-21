import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTransfertComponent } from 'app/entities/avis-transfert/avis-transfert.component';
import { AvisTransfertService } from 'app/entities/avis-transfert/avis-transfert.service';
import { AvisTransfert } from 'app/shared/model/avis-transfert.model';

describe('Component Tests', () => {
  describe('AvisTransfert Management Component', () => {
    let comp: AvisTransfertComponent;
    let fixture: ComponentFixture<AvisTransfertComponent>;
    let service: AvisTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTransfertComponent],
      })
        .overrideTemplate(AvisTransfertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTransfertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTransfertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvisTransfert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.avisTransferts && comp.avisTransferts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
