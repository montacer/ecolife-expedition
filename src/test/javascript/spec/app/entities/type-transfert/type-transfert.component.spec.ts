import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeTransfertComponent } from 'app/entities/type-transfert/type-transfert.component';
import { TypeTransfertService } from 'app/entities/type-transfert/type-transfert.service';
import { TypeTransfert } from 'app/shared/model/type-transfert.model';

describe('Component Tests', () => {
  describe('TypeTransfert Management Component', () => {
    let comp: TypeTransfertComponent;
    let fixture: ComponentFixture<TypeTransfertComponent>;
    let service: TypeTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeTransfertComponent],
      })
        .overrideTemplate(TypeTransfertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeTransfertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTransfertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeTransfert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeTransferts && comp.typeTransferts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
