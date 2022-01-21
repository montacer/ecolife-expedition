import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeTransfert } from 'app/shared/model/type-transfert.model';

type EntityResponseType = HttpResponse<ITypeTransfert>;
type EntityArrayResponseType = HttpResponse<ITypeTransfert[]>;

@Injectable({ providedIn: 'root' })
export class TypeTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/type-transferts';

  constructor(protected http: HttpClient) {}

  create(typeTransfert: ITypeTransfert): Observable<EntityResponseType> {
    return this.http.post<ITypeTransfert>(this.resourceUrl, typeTransfert, { observe: 'response' });
  }

  update(typeTransfert: ITypeTransfert): Observable<EntityResponseType> {
    return this.http.put<ITypeTransfert>(this.resourceUrl, typeTransfert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeTransfert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
