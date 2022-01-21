import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementDetailComponent } from 'app/entities/avis-hebergement/avis-hebergement-detail.component';
import { AvisHebergement } from 'app/shared/model/avis-hebergement.model';

describe('Component Tests', () => {
  describe('AvisHebergement Management Detail Component', () => {
    let comp: AvisHebergementDetailComponent;
    let fixture: ComponentFixture<AvisHebergementDetailComponent>;
    const route = ({ data: of({ avisHebergement: new AvisHebergement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisHebergementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisHebergementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avisHebergement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisHebergement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
