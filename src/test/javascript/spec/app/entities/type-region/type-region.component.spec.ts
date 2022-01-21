import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeRegionComponent } from 'app/entities/type-region/type-region.component';
import { TypeRegionService } from 'app/entities/type-region/type-region.service';
import { TypeRegion } from 'app/shared/model/type-region.model';

describe('Component Tests', () => {
  describe('TypeRegion Management Component', () => {
    let comp: TypeRegionComponent;
    let fixture: ComponentFixture<TypeRegionComponent>;
    let service: TypeRegionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeRegionComponent],
      })
        .overrideTemplate(TypeRegionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeRegionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeRegionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeRegion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeRegions && comp.typeRegions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
