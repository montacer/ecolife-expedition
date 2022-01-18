import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

type EntityResponseType = HttpResponse<ITarifServiceSupplementaire>;
type EntityArrayResponseType = HttpResponse<ITarifServiceSupplementaire[]>;

@Injectable({ providedIn: 'root' })
export class TarifServiceSupplementaireService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-service-supplementaires';

  constructor(protected http: HttpClient) {}

  create(tarifServiceSupplementaire: ITarifServiceSupplementaire): Observable<EntityResponseType> {
    return this.http.post<ITarifServiceSupplementaire>(this.resourceUrl, tarifServiceSupplementaire, { observe: 'response' });
  }

  update(tarifServiceSupplementaire: ITarifServiceSupplementaire): Observable<EntityResponseType> {
    return this.http.put<ITarifServiceSupplementaire>(this.resourceUrl, tarifServiceSupplementaire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifServiceSupplementaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifServiceSupplementaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
