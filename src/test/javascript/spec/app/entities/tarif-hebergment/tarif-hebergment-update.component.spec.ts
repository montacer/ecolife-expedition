import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifHebergmentUpdateComponent } from 'app/entities/tarif-hebergment/tarif-hebergment-update.component';
import { TarifHebergmentService } from 'app/entities/tarif-hebergment/tarif-hebergment.service';
import { TarifHebergment } from 'app/shared/model/tarif-hebergment.model';

describe('Component Tests', () => {
  describe('TarifHebergment Management Update Component', () => {
    let comp: TarifHebergmentUpdateComponent;
    let fixture: ComponentFixture<TarifHebergmentUpdateComponent>;
    let service: TarifHebergmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifHebergmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TarifHebergmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifHebergmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifHebergmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifHebergment(123);
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
        const entity = new TarifHebergment();
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
