import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisTour } from 'app/shared/model/avis-tour.model';

type EntityResponseType = HttpResponse<IAvisTour>;
type EntityArrayResponseType = HttpResponse<IAvisTour[]>;

@Injectable({ providedIn: 'root' })
export class AvisTourService {
  public resourceUrl = SERVER_API_URL + 'api/avis-tours';

  constructor(protected http: HttpClient) {}

  create(avisTour: IAvisTour): Observable<EntityResponseType> {
    return this.http.post<IAvisTour>(this.resourceUrl, avisTour, { observe: 'response' });
  }

  update(avisTour: IAvisTour): Observable<EntityResponseType> {
    return this.http.put<IAvisTour>(this.resourceUrl, avisTour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvisTour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvisTour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
