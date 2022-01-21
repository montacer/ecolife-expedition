import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisTransfert } from 'app/shared/model/avis-transfert.model';

type EntityResponseType = HttpResponse<IAvisTransfert>;
type EntityArrayResponseType = HttpResponse<IAvisTransfert[]>;

@Injectable({ providedIn: 'root' })
export class AvisTransfertService {
  public resourceUrl = SERVER_API_URL + 'api/avis-transferts';

  constructor(protected http: HttpClient) {}

  create(avisTransfert: IAvisTransfert): Observable<EntityResponseType> {
    return this.http.post<IAvisTransfert>(this.resourceUrl, avisTransfert, { observe: 'response' });
  }

  update(avisTransfert: IAvisTransfert): Observable<EntityResponseType> {
    return this.http.put<IAvisTransfert>(this.resourceUrl, avisTransfert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvisTransfert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvisTransfert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
