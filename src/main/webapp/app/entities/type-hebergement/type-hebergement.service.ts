import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';

type EntityResponseType = HttpResponse<ITypeHebergement>;
type EntityArrayResponseType = HttpResponse<ITypeHebergement[]>;

@Injectable({ providedIn: 'root' })
export class TypeHebergementService {
  public resourceUrl = SERVER_API_URL + 'api/type-hebergements';

  constructor(protected http: HttpClient) {}

  create(typeHebergement: ITypeHebergement): Observable<EntityResponseType> {
    return this.http.post<ITypeHebergement>(this.resourceUrl, typeHebergement, { observe: 'response' });
  }

  update(typeHebergement: ITypeHebergement): Observable<EntityResponseType> {
    return this.http.put<ITypeHebergement>(this.resourceUrl, typeHebergement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeHebergement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeHebergement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
