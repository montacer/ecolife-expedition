import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';

type EntityResponseType = HttpResponse<IReservationTransfert>;
type EntityArrayResponseType = HttpResponse<IReservationTransfert[]>;

@Injectable({ providedIn: 'root' })
export class ReservationTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/reservation-transferts';

  constructor(protected http: HttpClient) {}

  create(reservationTransfert: IReservationTransfert): Observable<EntityResponseType> {
    return this.http.post<IReservationTransfert>(this.resourceUrl, reservationTransfert, { observe: 'response' });
  }

  update(reservationTransfert: IReservationTransfert): Observable<EntityResponseType> {
    return this.http.put<IReservationTransfert>(this.resourceUrl, reservationTransfert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReservationTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReservationTransfert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
