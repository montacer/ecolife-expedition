import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeTarifComponent } from 'app/entities/type-tarif/type-tarif.component';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';
import { TypeTarif } from 'app/shared/model/type-tarif.model';

describe('Component Tests', () => {
  describe('TypeTarif Management Component', () => {
    let comp: TypeTarifComponent;
    let fixture: ComponentFixture<TypeTarifComponent>;
    let service: TypeTarifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeTarifComponent],
      })
        .overrideTemplate(TypeTarifComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeTarifComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTarifService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeTarif(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeTarifs && comp.typeTarifs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
