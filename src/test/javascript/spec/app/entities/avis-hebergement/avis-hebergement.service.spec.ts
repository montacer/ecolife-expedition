import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AvisHebergementService } from 'app/entities/avis-hebergement/avis-hebergement.service';
import { IAvisHebergement, AvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

describe('Service Tests', () => {
  describe('AvisHebergement Service', () => {
    let injector: TestBed;
    let service: AvisHebergementService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvisHebergement;
    let expectedResult: IAvisHebergement | IAvisHebergement[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvisHebergementService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AvisHebergement(0, 'AAAAAAA', Stars.ONE, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AvisHebergement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AvisHebergement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvisHebergement', () => {
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

      it('should return a list of AvisHebergement', () => {
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

      it('should delete a AvisHebergement', () => {
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
