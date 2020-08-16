import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';

type EntityResponseType = HttpResponse<IServiceSupplementaire>;
type EntityArrayResponseType = HttpResponse<IServiceSupplementaire[]>;

@Injectable({ providedIn: 'root' })
export class ServiceSupplementaireService {
  public resourceUrl = SERVER_API_URL + 'api/service-supplementaires';

  constructor(protected http: HttpClient) {}

  create(serviceSupplementaire: IServiceSupplementaire): Observable<EntityResponseType> {
    return this.http.post<IServiceSupplementaire>(this.resourceUrl, serviceSupplementaire, { observe: 'response' });
  }

  update(serviceSupplementaire: IServiceSupplementaire): Observable<EntityResponseType> {
    return this.http.put<IServiceSupplementaire>(this.resourceUrl, serviceSupplementaire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceSupplementaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceSupplementaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
