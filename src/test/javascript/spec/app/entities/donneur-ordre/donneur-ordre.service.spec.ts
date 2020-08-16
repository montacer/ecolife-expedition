import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DonneurOrdreService } from 'app/entities/donneur-ordre/donneur-ordre.service';
import { IDonneurOrdre, DonneurOrdre } from 'app/shared/model/donneur-ordre.model';

describe('Service Tests', () => {
  describe('DonneurOrdre Service', () => {
    let injector: TestBed;
    let service: DonneurOrdreService;
    let httpMock: HttpTestingController;
    let elemDefault: IDonneurOrdre;
    let expectedResult: IDonneurOrdre | IDonneurOrdre[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DonneurOrdreService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DonneurOrdre(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DonneurOrdre', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DonneurOrdre()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DonneurOrdre', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            numTelephone: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DonneurOrdre', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            numTelephone: 'BBBBBB',
            email: 'BBBBBB',
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

      it('should delete a DonneurOrdre', () => {
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
