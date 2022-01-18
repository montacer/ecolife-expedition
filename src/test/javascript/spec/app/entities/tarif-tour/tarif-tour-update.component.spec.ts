import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTourUpdateComponent } from 'app/entities/tarif-tour/tarif-tour-update.component';
import { TarifTourService } from 'app/entities/tarif-tour/tarif-tour.service';
import { TarifTour } from 'app/shared/model/tarif-tour.model';

describe('Component Tests', () => {
  describe('TarifTour Management Update Component', () => {
    let comp: TarifTourUpdateComponent;
    let fixture: ComponentFixture<TarifTourUpdateComponent>;
    let service: TarifTourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTourUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TarifTourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifTour(123);
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
        const entity = new TarifTour();
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
