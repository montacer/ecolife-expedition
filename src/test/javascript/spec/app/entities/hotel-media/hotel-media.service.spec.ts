import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HotelMediaService } from 'app/entities/hotel-media/hotel-media.service';
import { IHotelMedia, HotelMedia } from 'app/shared/model/hotel-media.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

describe('Service Tests', () => {
  describe('HotelMedia Service', () => {
    let injector: TestBed;
    let service: HotelMediaService;
    let httpMock: HttpTestingController;
    let elemDefault: IHotelMedia;
    let expectedResult: IHotelMedia | IHotelMedia[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HotelMediaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new HotelMedia(0, 'AAAAAAA', MediaType.TEXT, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HotelMedia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HotelMedia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HotelMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaUrl: 'BBBBBB',
            mediaType: 'BBBBBB',
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

      it('should return a list of HotelMedia', () => {
        const returnedFromService = Object.assign(
          {
            mediaUrl: 'BBBBBB',
            mediaType: 'BBBBBB',
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

      it('should delete a HotelMedia', () => {
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
