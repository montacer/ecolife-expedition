import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TarifTransfertUpdateComponent } from 'app/entities/tarif-transfert/tarif-transfert-update.component';
import { TarifTransfertService } from 'app/entities/tarif-transfert/tarif-transfert.service';
import { TarifTransfert } from 'app/shared/model/tarif-transfert.model';

describe('Component Tests', () => {
  describe('TarifTransfert Management Update Component', () => {
    let comp: TarifTransfertUpdateComponent;
    let fixture: ComponentFixture<TarifTransfertUpdateComponent>;
    let service: TarifTransfertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TarifTransfertUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TarifTransfertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTransfertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTransfertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifTransfert(123);
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
        const entity = new TarifTransfert();
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
