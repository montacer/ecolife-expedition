import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeHebergementUpdateComponent } from 'app/entities/type-hebergement/type-hebergement-update.component';
import { TypeHebergementService } from 'app/entities/type-hebergement/type-hebergement.service';
import { TypeHebergement } from 'app/shared/model/type-hebergement.model';

describe('Component Tests', () => {
  describe('TypeHebergement Management Update Component', () => {
    let comp: TypeHebergementUpdateComponent;
    let fixture: ComponentFixture<TypeHebergementUpdateComponent>;
    let service: TypeHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeHebergementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeHebergementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeHebergementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeHebergementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeHebergement(123);
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
        const entity = new TypeHebergement();
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
