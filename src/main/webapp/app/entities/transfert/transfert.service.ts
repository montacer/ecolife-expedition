import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransfert } from 'app/shared/model/transfert.model';

type EntityResponseType = HttpResponse<ITransfert>;
type EntityArrayResponseType = HttpResponse<ITransfert[]>;

@Injectable({ providedIn: 'root' })
export class TransfertService {
  public resourceUrl = SERVER_API_URL + 'api/transferts';

  constructor(protected http: HttpClient) {}

  create(transfert: ITransfert): Observable<EntityResponseType> {
    return this.http.post<ITransfert>(this.resourceUrl, transfert, { observe: 'response' });
  }

  update(transfert: ITransfert): Observable<EntityResponseType> {
    return this.http.put<ITransfert>(this.resourceUrl, transfert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransfert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
