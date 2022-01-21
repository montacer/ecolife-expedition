import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergmentComponent } from 'app/entities/tarif-hebergment/tarif-hebergment.component';
import { TarifHebergmentService } from 'app/entities/tarif-hebergment/tarif-hebergment.service';
import { TarifHebergment } from 'app/shared/model/tarif-hebergment.model';

describe('Component Tests', () => {
  describe('TarifHebergment Management Component', () => {
    let comp: TarifHebergmentComponent;
    let fixture: ComponentFixture<TarifHebergmentComponent>;
    let service: TarifHebergmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergmentComponent],
      })
        .overrideTemplate(TarifHebergmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifHebergmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifHebergmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TarifHebergment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tarifHebergments && comp.tarifHebergments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
