import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';

type EntityResponseType = HttpResponse<ITypeChambre>;
type EntityArrayResponseType = HttpResponse<ITypeChambre[]>;

@Injectable({ providedIn: 'root' })
export class TypeChambreService {
  public resourceUrl = SERVER_API_URL + 'api/type-chambres';

  constructor(protected http: HttpClient) {}

  create(typeChambre: ITypeChambre): Observable<EntityResponseType> {
    return this.http.post<ITypeChambre>(this.resourceUrl, typeChambre, { observe: 'response' });
  }

  update(typeChambre: ITypeChambre): Observable<EntityResponseType> {
    return this.http.put<ITypeChambre>(this.resourceUrl, typeChambre, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeChambre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeChambre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
