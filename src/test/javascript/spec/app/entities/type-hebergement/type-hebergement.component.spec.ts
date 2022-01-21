import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeHebergementComponent } from 'app/entities/type-hebergement/type-hebergement.component';
import { TypeHebergementService } from 'app/entities/type-hebergement/type-hebergement.service';
import { TypeHebergement } from 'app/shared/model/type-hebergement.model';

describe('Component Tests', () => {
  describe('TypeHebergement Management Component', () => {
    let comp: TypeHebergementComponent;
    let fixture: ComponentFixture<TypeHebergementComponent>;
    let service: TypeHebergementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeHebergementComponent],
      })
        .overrideTemplate(TypeHebergementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeHebergementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeHebergementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeHebergement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeHebergements && comp.typeHebergements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
