import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourUpdateComponent } from 'app/entities/avis-tour/avis-tour-update.component';
import { AvisTourService } from 'app/entities/avis-tour/avis-tour.service';
import { AvisTour } from 'app/shared/model/avis-tour.model';

describe('Component Tests', () => {
  describe('AvisTour Management Update Component', () => {
    let comp: AvisTourUpdateComponent;
    let fixture: ComponentFixture<AvisTourUpdateComponent>;
    let service: AvisTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisTourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisTour(123);
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
        const entity = new AvisTour();
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
