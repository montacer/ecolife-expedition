import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TransfertComponent } from 'app/entities/transfert/transfert.component';
import { TransfertService } from 'app/entities/transfert/transfert.service';
import { Transfert } from 'app/shared/model/transfert.model';

describe('Component Tests', () => {
  describe('Transfert Management Component', () => {
    let comp: TransfertComponent;
    let fixture: ComponentFixture<TransfertComponent>;
    let service: TransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TransfertComponent],
      })
        .overrideTemplate(TransfertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransfertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransfertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transfert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transferts && comp.transferts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
