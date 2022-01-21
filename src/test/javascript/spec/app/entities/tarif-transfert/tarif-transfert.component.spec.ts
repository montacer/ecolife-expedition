import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTransfertComponent } from 'app/entities/tarif-transfert/tarif-transfert.component';
import { TarifTransfertService } from 'app/entities/tarif-transfert/tarif-transfert.service';
import { TarifTransfert } from 'app/shared/model/tarif-transfert.model';

describe('Component Tests', () => {
  describe('TarifTransfert Management Component', () => {
    let comp: TarifTransfertComponent;
    let fixture: ComponentFixture<TarifTransfertComponent>;
    let service: TarifTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTransfertComponent],
      })
        .overrideTemplate(TarifTransfertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTransfertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTransfertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TarifTransfert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tarifTransferts && comp.tarifTransferts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
