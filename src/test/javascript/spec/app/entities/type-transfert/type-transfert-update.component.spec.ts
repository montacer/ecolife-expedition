import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeTransfertUpdateComponent } from 'app/entities/type-transfert/type-transfert-update.component';
import { TypeTransfertService } from 'app/entities/type-transfert/type-transfert.service';
import { TypeTransfert } from 'app/shared/model/type-transfert.model';

describe('Component Tests', () => {
  describe('TypeTransfert Management Update Component', () => {
    let comp: TypeTransfertUpdateComponent;
    let fixture: ComponentFixture<TypeTransfertUpdateComponent>;
    let service: TypeTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeTransfertUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeTransfert(123);
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
        const entity = new TypeTransfert();
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
