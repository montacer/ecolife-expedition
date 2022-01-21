import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeRegionUpdateComponent } from 'app/entities/type-region/type-region-update.component';
import { TypeRegionService } from 'app/entities/type-region/type-region.service';
import { TypeRegion } from 'app/shared/model/type-region.model';

describe('Component Tests', () => {
  describe('TypeRegion Management Update Component', () => {
    let comp: TypeRegionUpdateComponent;
    let fixture: ComponentFixture<TypeRegionUpdateComponent>;
    let service: TypeRegionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeRegionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeRegionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeRegionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeRegionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeRegion(123);
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
        const entity = new TypeRegion();
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
