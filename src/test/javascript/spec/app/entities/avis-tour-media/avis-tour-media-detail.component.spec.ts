import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { AvisTourMediaDetailComponent } from 'app/entities/avis-tour-media/avis-tour-media-detail.component';
import { AvisTourMedia } from 'app/shared/model/avis-tour-media.model';

describe('Component Tests', () => {
  describe('AvisTourMedia Management Detail Component', () => {
    let comp: AvisTourMediaDetailComponent;
    let fixture: ComponentFixture<AvisTourMediaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ avisTourMedia: new AvisTourMedia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [AvisTourMediaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisTourMediaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisTourMediaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load avisTourMedia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisTourMedia).toEqual(jasmine.objectContaining({ id: 123 }));
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
