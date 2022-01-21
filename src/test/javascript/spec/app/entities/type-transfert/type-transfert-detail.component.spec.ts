import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { TypeTransfertDetailComponent } from 'app/entities/type-transfert/type-transfert-detail.component';
import { TypeTransfert } from 'app/shared/model/type-transfert.model';

describe('Component Tests', () => {
  describe('TypeTransfert Management Detail Component', () => {
    let comp: TypeTransfertDetailComponent;
    let fixture: ComponentFixture<TypeTransfertDetailComponent>;
    const route = ({ data: of({ typeTransfert: new TypeTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [TypeTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeTransfert on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
