import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergementComponent } from 'app/entities/tarif-hebergement/tarif-hebergement.component';
import { TarifHebergementService } from 'app/entities/tarif-hebergement/tarif-hebergement.service';
import { TarifHebergement } from 'app/shared/model/tarif-hebergement.model';

describe('Component Tests', () => {
  describe('TarifHebergement Management Component', () => {
    let comp: TarifHebergementComponent;
    let fixture: ComponentFixture<TarifHebergementComponent>;
    let service: TarifHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergementComponent],
      })
        .overrideTemplate(TarifHebergementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifHebergementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifHebergementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TarifHebergement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tarifHebergements && comp.tarifHebergements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
