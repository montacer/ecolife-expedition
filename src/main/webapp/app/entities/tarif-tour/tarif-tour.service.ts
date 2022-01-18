import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifTour } from 'app/shared/model/tarif-tour.model';

type EntityResponseType = HttpResponse<ITarifTour>;
type EntityArrayResponseType = HttpResponse<ITarifTour[]>;

@Injectable({ providedIn: 'root' })
export class TarifTourService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-tours';

  constructor(protected http: HttpClient) {}

  create(tarifTour: ITarifTour): Observable<EntityResponseType> {
    return this.http.post<ITarifTour>(this.resourceUrl, tarifTour, { observe: 'response' });
  }

  update(tarifTour: ITarifTour): Observable<EntityResponseType> {
    return this.http.put<ITarifTour>(this.resourceUrl, tarifTour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifTour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifTour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
