import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AvisTourMediaService } from 'app/entities/avis-tour-media/avis-tour-media.service';
import { IAvisTourMedia, AvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

describe('Service Tests', () => {
  describe('AvisTourMedia Service', () => {
    let injector: TestBed;
    let service: AvisTourMediaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvisTourMedia;
    let expectedResult: IAvisTourMedia | IAvisTourMedia[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvisTourMediaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AvisTourMedia(0, MediaType.TEXT, 'AAAAAAA', 'image/png', 'AAAAAAA', Stars.ONE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AvisTourMedia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AvisTourMedia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvisTourMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaType: 'BBBBBB',
            mediaUrl: 'BBBBBB',
            mediacontent: 'BBBBBB',
            score: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AvisTourMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaType: 'BBBBBB',
            mediaUrl: 'BBBBBB',
            mediacontent: 'BBBBBB',
            score: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AvisTourMedia', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
