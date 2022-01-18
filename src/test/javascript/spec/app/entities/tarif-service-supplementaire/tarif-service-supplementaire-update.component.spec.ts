import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifServiceSupplementaireUpdateComponent } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire-update.component';
import { TarifServiceSupplementaireService } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire.service';
import { TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

describe('Component Tests', () => {
  describe('TarifServiceSupplementaire Management Update Component', () => {
    let comp: TarifServiceSupplementaireUpdateComponent;
    let fixture: ComponentFixture<TarifServiceSupplementaireUpdateComponent>;
    let service: TarifServiceSupplementaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifServiceSupplementaireUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TarifServiceSupplementaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifServiceSupplementaireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifServiceSupplementaireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifServiceSupplementaire(123);
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
        const entity = new TarifServiceSupplementaire();
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
