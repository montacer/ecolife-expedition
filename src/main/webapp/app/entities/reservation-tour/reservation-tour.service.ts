import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReservationTour } from 'app/shared/model/reservation-tour.model';

type EntityResponseType = HttpResponse<IReservationTour>;
type EntityArrayResponseType = HttpResponse<IReservationTour[]>;

@Injectable({ providedIn: 'root' })
export class ReservationTourService {
  public resourceUrl = SERVER_API_URL + 'api/reservation-tours';

  constructor(protected http: HttpClient) {}

  create(reservationTour: IReservationTour): Observable<EntityResponseType> {
    return this.http.post<IReservationTour>(this.resourceUrl, reservationTour, { observe: 'response' });
  }

  update(reservationTour: IReservationTour): Observable<EntityResponseType> {
    return this.http.put<IReservationTour>(this.resourceUrl, reservationTour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReservationTour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReservationTour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
