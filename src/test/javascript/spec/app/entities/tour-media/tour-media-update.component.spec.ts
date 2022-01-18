import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TourMediaUpdateComponent } from 'app/entities/tour-media/tour-media-update.component';
import { TourMediaService } from 'app/entities/tour-media/tour-media.service';
import { TourMedia } from 'app/shared/model/tour-media.model';

describe('Component Tests', () => {
  describe('TourMedia Management Update Component', () => {
    let comp: TourMediaUpdateComponent;
    let fixture: ComponentFixture<TourMediaUpdateComponent>;
    let service: TourMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TourMediaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TourMediaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourMediaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourMediaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TourMedia(123);
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
        const entity = new TourMedia();
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
