import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';

type EntityResponseType = HttpResponse<ITypeTarif>;
type EntityArrayResponseType = HttpResponse<ITypeTarif[]>;

@Injectable({ providedIn: 'root' })
export class TypeTarifService {
  public resourceUrl = SERVER_API_URL + 'api/type-tarifs';

  constructor(protected http: HttpClient) {}

  create(typeTarif: ITypeTarif): Observable<EntityResponseType> {
    return this.http.post<ITypeTarif>(this.resourceUrl, typeTarif, { observe: 'response' });
  }

  update(typeTarif: ITypeTarif): Observable<EntityResponseType> {
    return this.http.put<ITypeTarif>(this.resourceUrl, typeTarif, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeTarif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeTarif[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
