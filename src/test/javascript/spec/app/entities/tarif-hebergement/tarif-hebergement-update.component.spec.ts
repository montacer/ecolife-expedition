import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergementUpdateComponent } from 'app/entities/tarif-hebergement/tarif-hebergement-update.component';
import { TarifHebergementService } from 'app/entities/tarif-hebergement/tarif-hebergement.service';
import { TarifHebergement } from 'app/shared/model/tarif-hebergement.model';

describe('Component Tests', () => {
  describe('TarifHebergement Management Update Component', () => {
    let comp: TarifHebergementUpdateComponent;
    let fixture: ComponentFixture<TarifHebergementUpdateComponent>;
    let service: TarifHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TarifHebergementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifHebergementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifHebergementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifHebergement(123);
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
        const entity = new TarifHebergement();
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
