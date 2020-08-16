import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeCircuitDetailComponent } from 'app/entities/type-circuit/type-circuit-detail.component';
import { TypeCircuit } from 'app/shared/model/type-circuit.model';

describe('Component Tests', () => {
  describe('TypeCircuit Management Detail Component', () => {
    let comp: TypeCircuitDetailComponent;
    let fixture: ComponentFixture<TypeCircuitDetailComponent>;
    const route = ({ data: of({ typeCircuit: new TypeCircuit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeCircuitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeCircuitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeCircuitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeCircuit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeCircuit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
