import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeCircuitComponent } from 'app/entities/type-circuit/type-circuit.component';
import { TypeCircuitService } from 'app/entities/type-circuit/type-circuit.service';
import { TypeCircuit } from 'app/shared/model/type-circuit.model';

describe('Component Tests', () => {
  describe('TypeCircuit Management Component', () => {
    let comp: TypeCircuitComponent;
    let fixture: ComponentFixture<TypeCircuitComponent>;
    let service: TypeCircuitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeCircuitComponent],
      })
        .overrideTemplate(TypeCircuitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeCircuitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeCircuitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeCircuit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeCircuits && comp.typeCircuits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
