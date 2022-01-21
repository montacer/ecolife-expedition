import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ReservationTransfertUpdateComponent } from 'app/entities/reservation-transfert/reservation-transfert-update.component';
import { ReservationTransfertService } from 'app/entities/reservation-transfert/reservation-transfert.service';
import { ReservationTransfert } from 'app/shared/model/reservation-transfert.model';

describe('Component Tests', () => {
  describe('ReservationTransfert Management Update Component', () => {
    let comp: ReservationTransfertUpdateComponent;
    let fixture: ComponentFixture<ReservationTransfertUpdateComponent>;
    let service: ReservationTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ReservationTransfertUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReservationTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservationTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReservationTransfert(123);
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
        const entity = new ReservationTransfert();
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
