import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { HotelMediaDetailComponent } from 'app/entities/hotel-media/hotel-media-detail.component';
import { HotelMedia } from 'app/shared/model/hotel-media.model';

describe('Component Tests', () => {
  describe('HotelMedia Management Detail Component', () => {
    let comp: HotelMediaDetailComponent;
    let fixture: ComponentFixture<HotelMediaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ hotelMedia: new HotelMedia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [HotelMediaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HotelMediaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HotelMediaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load hotelMedia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hotelMedia).toEqual(jasmine.objectContaining({ id: 123 }));
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
