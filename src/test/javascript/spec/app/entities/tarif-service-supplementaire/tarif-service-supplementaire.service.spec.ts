import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TarifServiceSupplementaireService } from 'app/entities/tarif-service-supplementaire/tarif-service-supplementaire.service';
import { ITarifServiceSupplementaire, TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

describe('Service Tests', () => {
  describe('TarifServiceSupplementaire Service', () => {
    let injector: TestBed;
    let service: TarifServiceSupplementaireService;
    let httpMock: HttpTestingController;
    let elemDefault: ITarifServiceSupplementaire;
    let expectedResult: ITarifServiceSupplementaire | ITarifServiceSupplementaire[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TarifServiceSupplementaireService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TarifServiceSupplementaire(0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TarifServiceSupplementaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TarifServiceSupplementaire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TarifServiceSupplementaire', () => {
        const returnedFromService = Object.assign(
          {
            montantTTC: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TarifServiceSupplementaire', () => {
        const returnedFromService = Object.assign(
          {
            montantTTC: 1,
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

      it('should delete a TarifServiceSupplementaire', () => {
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
