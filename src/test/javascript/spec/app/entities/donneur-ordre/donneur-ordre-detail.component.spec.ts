import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { DonneurOrdreDetailComponent } from 'app/entities/donneur-ordre/donneur-ordre-detail.component';
import { DonneurOrdre } from 'app/shared/model/donneur-ordre.model';

describe('Component Tests', () => {
  describe('DonneurOrdre Management Detail Component', () => {
    let comp: DonneurOrdreDetailComponent;
    let fixture: ComponentFixture<DonneurOrdreDetailComponent>;
    const route = ({ data: of({ donneurOrdre: new DonneurOrdre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [DonneurOrdreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DonneurOrdreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DonneurOrdreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load donneurOrdre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.donneurOrdre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
