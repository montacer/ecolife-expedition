import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { HotelMediaUpdateComponent } from 'app/entities/hotel-media/hotel-media-update.component';
import { HotelMediaService } from 'app/entities/hotel-media/hotel-media.service';
import { HotelMedia } from 'app/shared/model/hotel-media.model';

describe('Component Tests', () => {
  describe('HotelMedia Management Update Component', () => {
    let comp: HotelMediaUpdateComponent;
    let fixture: ComponentFixture<HotelMediaUpdateComponent>;
    let service: HotelMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [HotelMediaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HotelMediaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelMediaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HotelMediaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HotelMedia(123);
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
        const entity = new HotelMedia();
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
