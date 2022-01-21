import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';

type EntityResponseType = HttpResponse<ITarifTransfert>;
type EntityArrayResponseType = HttpResponse<ITarifTransfert[]>;

@Injectable({ providedIn: 'root' })
export class TarifTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-transferts';

  constructor(protected http: HttpClient) {}

  create(tarifTransfert: ITarifTransfert): Observable<EntityResponseType> {
    return this.http.post<ITarifTransfert>(this.resourceUrl, tarifTransfert, { observe: 'response' });
  }

  update(tarifTransfert: ITarifTransfert): Observable<EntityResponseType> {
    return this.http.put<ITarifTransfert>(this.resourceUrl, tarifTransfert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifTransfert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
