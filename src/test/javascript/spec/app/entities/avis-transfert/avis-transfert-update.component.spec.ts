import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTransfertUpdateComponent } from 'app/entities/avis-transfert/avis-transfert-update.component';
import { AvisTransfertService } from 'app/entities/avis-transfert/avis-transfert.service';
import { AvisTransfert } from 'app/shared/model/avis-transfert.model';

describe('Component Tests', () => {
  describe('AvisTransfert Management Update Component', () => {
    let comp: AvisTransfertUpdateComponent;
    let fixture: ComponentFixture<AvisTransfertUpdateComponent>;
    let service: AvisTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTransfertUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisTransfert(123);
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
        const entity = new AvisTransfert();
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
