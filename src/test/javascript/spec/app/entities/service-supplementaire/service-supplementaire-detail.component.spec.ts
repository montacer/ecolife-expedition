import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { ServiceSupplementaireDetailComponent } from 'app/entities/service-supplementaire/service-supplementaire-detail.component';
import { ServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';

describe('Component Tests', () => {
  describe('ServiceSupplementaire Management Detail Component', () => {
    let comp: ServiceSupplementaireDetailComponent;
    let fixture: ComponentFixture<ServiceSupplementaireDetailComponent>;
    const route = ({ data: of({ serviceSupplementaire: new ServiceSupplementaire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ServiceSupplementaireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServiceSupplementaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceSupplementaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceSupplementaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceSupplementaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
