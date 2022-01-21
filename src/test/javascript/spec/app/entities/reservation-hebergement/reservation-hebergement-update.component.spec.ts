import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationHebergementUpdateComponent } from 'app/entities/reservation-hebergement/reservation-hebergement-update.component';
import { ReservationHebergementService } from 'app/entities/reservation-hebergement/reservation-hebergement.service';
import { ReservationHebergement } from 'app/shared/model/reservation-hebergement.model';

describe('Component Tests', () => {
  describe('ReservationHebergement Management Update Component', () => {
    let comp: ReservationHebergementUpdateComponent;
    let fixture: ComponentFixture<ReservationHebergementUpdateComponent>;
    let service: ReservationHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationHebergementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReservationHebergementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationHebergementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationHebergementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReservationHebergement(123);
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
        const entity = new ReservationHebergement();
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
