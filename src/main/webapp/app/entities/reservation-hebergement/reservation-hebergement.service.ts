import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';

type EntityResponseType = HttpResponse<IReservationHebergement>;
type EntityArrayResponseType = HttpResponse<IReservationHebergement[]>;

@Injectable({ providedIn: 'root' })
export class ReservationHebergementService {
  public resourceUrl = SERVER_API_URL + 'api/reservation-hebergements';

  constructor(protected http: HttpClient) {}

  create(reservationHebergement: IReservationHebergement): Observable<EntityResponseType> {
    return this.http.post<IReservationHebergement>(this.resourceUrl, reservationHebergement, { observe: 'response' });
  }

  update(reservationHebergement: IReservationHebergement): Observable<EntityResponseType> {
    return this.http.put<IReservationHebergement>(this.resourceUrl, reservationHebergement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReservationHebergement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReservationHebergement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
