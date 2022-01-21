import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTransfertDetailComponent } from 'app/entities/avis-transfert/avis-transfert-detail.component';
import { AvisTransfert } from 'app/shared/model/avis-transfert.model';

describe('Component Tests', () => {
  describe('AvisTransfert Management Detail Component', () => {
    let comp: AvisTransfertDetailComponent;
    let fixture: ComponentFixture<AvisTransfertDetailComponent>;
    const route = ({ data: of({ avisTransfert: new AvisTransfert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTransfertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisTransfertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisTransfertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avisTransfert on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisTransfert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
