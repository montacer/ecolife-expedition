import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourMediaUpdateComponent } from 'app/entities/avis-tour-media/avis-tour-media-update.component';
import { AvisTourMediaService } from 'app/entities/avis-tour-media/avis-tour-media.service';
import { AvisTourMedia } from 'app/shared/model/avis-tour-media.model';

describe('Component Tests', () => {
  describe('AvisTourMedia Management Update Component', () => {
    let comp: AvisTourMediaUpdateComponent;
    let fixture: ComponentFixture<AvisTourMediaUpdateComponent>;
    let service: AvisTourMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourMediaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisTourMediaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTourMediaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTourMediaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisTourMedia(123);
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
        const entity = new AvisTourMedia();
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
