import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeRegionDetailComponent } from 'app/entities/type-region/type-region-detail.component';
import { TypeRegion } from 'app/shared/model/type-region.model';

describe('Component Tests', () => {
  describe('TypeRegion Management Detail Component', () => {
    let comp: TypeRegionDetailComponent;
    let fixture: ComponentFixture<TypeRegionDetailComponent>;
    const route = ({ data: of({ typeRegion: new TypeRegion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeRegionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeRegionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeRegionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeRegion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeRegion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
