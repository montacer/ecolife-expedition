import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TourUpdateComponent } from 'app/entities/tour/tour-update.component';
import { TourService } from 'app/entities/tour/tour.service';
import { Tour } from 'app/shared/model/tour.model';

describe('Component Tests', () => {
  describe('Tour Management Update Component', () => {
    let comp: TourUpdateComponent;
    let fixture: ComponentFixture<TourUpdateComponent>;
    let service: TourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TourUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tour(123);
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
        const entity = new Tour();
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
