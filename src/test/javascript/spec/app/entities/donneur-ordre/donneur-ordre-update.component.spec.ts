import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { DonneurOrdreUpdateComponent } from 'app/entities/donneur-ordre/donneur-ordre-update.component';
import { DonneurOrdreService } from 'app/entities/donneur-ordre/donneur-ordre.service';
import { DonneurOrdre } from 'app/shared/model/donneur-ordre.model';

describe('Component Tests', () => {
  describe('DonneurOrdre Management Update Component', () => {
    let comp: DonneurOrdreUpdateComponent;
    let fixture: ComponentFixture<DonneurOrdreUpdateComponent>;
    let service: DonneurOrdreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [DonneurOrdreUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DonneurOrdreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonneurOrdreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonneurOrdreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DonneurOrdre(123);
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
        const entity = new DonneurOrdre();
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
