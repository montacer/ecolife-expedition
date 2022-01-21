import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisHebergementMediaDetailComponent } from 'app/entities/avis-hebergement-media/avis-hebergement-media-detail.component';
import { AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';

describe('Component Tests', () => {
  describe('AvisHebergementMedia Management Detail Component', () => {
    let comp: AvisHebergementMediaDetailComponent;
    let fixture: ComponentFixture<AvisHebergementMediaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ avisHebergementMedia: new AvisHebergementMedia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisHebergementMediaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisHebergementMediaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisHebergementMediaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load avisHebergementMedia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisHebergementMedia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
