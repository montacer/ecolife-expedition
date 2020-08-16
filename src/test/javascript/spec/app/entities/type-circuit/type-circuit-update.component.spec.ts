import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeCircuitUpdateComponent } from 'app/entities/type-circuit/type-circuit-update.component';
import { TypeCircuitService } from 'app/entities/type-circuit/type-circuit.service';
import { TypeCircuit } from 'app/shared/model/type-circuit.model';

describe('Component Tests', () => {
  describe('TypeCircuit Management Update Component', () => {
    let comp: TypeCircuitUpdateComponent;
    let fixture: ComponentFixture<TypeCircuitUpdateComponent>;
    let service: TypeCircuitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeCircuitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeCircuitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeCircuitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeCircuitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeCircuit(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeCircuit();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
