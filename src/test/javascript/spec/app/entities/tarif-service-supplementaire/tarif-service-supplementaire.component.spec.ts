import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifServiceSupplementaireComponent } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire.component';
import { TarifServiceSupplementaireService } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire.service';
import { TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

describe('Component Tests', () => {
  describe('TarifServiceSupplementaire Management Component', () => {
    let comp: TarifServiceSupplementaireComponent;
    let fixture: ComponentFixture<TarifServiceSupplementaireComponent>;
    let service: TarifServiceSupplementaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifServiceSupplementaireComponent],
      })
        .overrideTemplate(TarifServiceSupplementaireComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifServiceSupplementaireComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifServiceSupplementaireService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TarifServiceSupplementaire(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tarifServiceSupplementaires && comp.tarifServiceSupplementaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
