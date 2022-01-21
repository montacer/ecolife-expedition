import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AvisTransfertService } from 'app/entities/avis-transfert/avis-transfert.service';
import { IAvisTransfert, AvisTransfert } from 'app/shared/model/avis-transfert.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

describe('Service Tests', () => {
  describe('AvisTransfert Service', () => {
    let injector: TestBed;
    let service: AvisTransfertService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvisTransfert;
    let expectedResult: IAvisTransfert | IAvisTransfert[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvisTransfertService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AvisTransfert(0, 'AAAAAAA', Stars.ONE, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AvisTransfert', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AvisTransfert()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvisTransfert', () => {
        const returnedFromService = Object.assign(
          {
            author: 'BBBBBB',
            nbreEtoile: 'BBBBBB',
            commentaire: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AvisTransfert', () => {
        const returnedFromService = Object.assign(
          {
            author: 'BBBBBB',
            nbreEtoile: 'BBBBBB',
            commentaire: 'BBBBBB',
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

      it('should delete a AvisTransfert', () => {
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
