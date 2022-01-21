import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AvisHebergementMediaService } from 'app/entities/avis-hebergement-media/avis-hebergement-media.service';
import { IAvisHebergementMedia, AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

describe('Service Tests', () => {
  describe('AvisHebergementMedia Service', () => {
    let injector: TestBed;
    let service: AvisHebergementMediaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvisHebergementMedia;
    let expectedResult: IAvisHebergementMedia | IAvisHebergementMedia[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvisHebergementMediaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AvisHebergementMedia(0, MediaType.TEXT, 'AAAAAAA', 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AvisHebergementMedia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AvisHebergementMedia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvisHebergementMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaType: 'BBBBBB',
            mediaUrl: 'BBBBBB',
            mediacontent: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AvisHebergementMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaType: 'BBBBBB',
            mediaUrl: 'BBBBBB',
            mediacontent: 'BBBBBB',
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

      it('should delete a AvisHebergementMedia', () => {
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
