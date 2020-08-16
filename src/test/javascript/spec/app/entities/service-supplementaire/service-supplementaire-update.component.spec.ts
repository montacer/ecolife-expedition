import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ServiceSupplementaireUpdateComponent } from 'app/entities/service-supplementaire/service-supplementaire-update.component';
import { ServiceSupplementaireService } from 'app/entities/service-supplementaire/service-supplementaire.service';
import { ServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';

describe('Component Tests', () => {
  describe('ServiceSupplementaire Management Update Component', () => {
    let comp: ServiceSupplementaireUpdateComponent;
    let fixture: ComponentFixture<ServiceSupplementaireUpdateComponent>;
    let service: ServiceSupplementaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ServiceSupplementaireUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServiceSupplementaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceSupplementaireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupplementaireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceSupplementaire(123);
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
        const entity = new ServiceSupplementaire();
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
