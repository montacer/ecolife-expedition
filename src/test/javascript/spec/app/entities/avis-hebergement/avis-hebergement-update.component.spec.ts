import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementUpdateComponent } from 'app/entities/avis-hebergement/avis-hebergement-update.component';
import { AvisHebergementService } from 'app/entities/avis-hebergement/avis-hebergement.service';
import { AvisHebergement } from 'app/shared/model/avis-hebergement.model';

describe('Component Tests', () => {
  describe('AvisHebergement Management Update Component', () => {
    let comp: AvisHebergementUpdateComponent;
    let fixture: ComponentFixture<AvisHebergementUpdateComponent>;
    let service: AvisHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisHebergementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisHebergementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisHebergementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisHebergement(123);
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
        const entity = new AvisHebergement();
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
