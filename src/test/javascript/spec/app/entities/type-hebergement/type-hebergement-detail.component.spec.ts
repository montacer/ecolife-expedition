import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeHebergementDetailComponent } from 'app/entities/type-hebergement/type-hebergement-detail.component';
import { TypeHebergement } from 'app/shared/model/type-hebergement.model';

describe('Component Tests', () => {
  describe('TypeHebergement Management Detail Component', () => {
    let comp: TypeHebergementDetailComponent;
    let fixture: ComponentFixture<TypeHebergementDetailComponent>;
    const route = ({ data: of({ typeHebergement: new TypeHebergement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeHebergementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeHebergementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeHebergementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeHebergement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeHebergement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
