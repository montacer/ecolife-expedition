import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTourUpdateComponent } from 'app/entities/reservation-tour/reservation-tour-update.component';
import { ReservationTourService } from 'app/entities/reservation-tour/reservation-tour.service';
import { ReservationTour } from 'app/shared/model/reservation-tour.model';

describe('Component Tests', () => {
  describe('ReservationTour Management Update Component', () => {
    let comp: ReservationTourUpdateComponent;
    let fixture: ComponentFixture<ReservationTourUpdateComponent>;
    let service: ReservationTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTourUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReservationTourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationTourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationTourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReservationTour(123);
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
        const entity = new ReservationTour();
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
