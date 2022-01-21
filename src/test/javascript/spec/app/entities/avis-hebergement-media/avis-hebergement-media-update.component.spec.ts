import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementMediaUpdateComponent } from 'app/entities/avis-hebergement-media/avis-hebergement-media-update.component';
import { AvisHebergementMediaService } from 'app/entities/avis-hebergement-media/avis-hebergement-media.service';
import { AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';

describe('Component Tests', () => {
  describe('AvisHebergementMedia Management Update Component', () => {
    let comp: AvisHebergementMediaUpdateComponent;
    let fixture: ComponentFixture<AvisHebergementMediaUpdateComponent>;
    let service: AvisHebergementMediaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementMediaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisHebergementMediaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisHebergementMediaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisHebergementMediaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisHebergementMedia(123);
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
        const entity = new AvisHebergementMedia();
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
