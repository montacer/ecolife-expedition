import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourDetailComponent } from 'app/entities/avis-tour/avis-tour-detail.component';
import { AvisTour } from 'app/shared/model/avis-tour.model';

describe('Component Tests', () => {
  describe('AvisTour Management Detail Component', () => {
    let comp: AvisTourDetailComponent;
    let fixture: ComponentFixture<AvisTourDetailComponent>;
    const route = ({ data: of({ avisTour: new AvisTour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisTourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisTourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avisTour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisTour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
