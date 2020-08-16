import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { DonneurOrdreComponent } from 'app/entities/donneur-ordre/donneur-ordre.component';
import { DonneurOrdreService } from 'app/entities/donneur-ordre/donneur-ordre.service';
import { DonneurOrdre } from 'app/shared/model/donneur-ordre.model';

describe('Component Tests', () => {
  describe('DonneurOrdre Management Component', () => {
    let comp: DonneurOrdreComponent;
    let fixture: ComponentFixture<DonneurOrdreComponent>;
    let service: DonneurOrdreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [DonneurOrdreComponent],
      })
        .overrideTemplate(DonneurOrdreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonneurOrdreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonneurOrdreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DonneurOrdre(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.donneurOrdres && comp.donneurOrdres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
