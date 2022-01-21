import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifHebergment } from 'app/shared/model/tarif-hebergment.model';

type EntityResponseType = HttpResponse<ITarifHebergment>;
type EntityArrayResponseType = HttpResponse<ITarifHebergment[]>;

@Injectable({ providedIn: 'root' })
export class TarifHebergmentService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-hebergments';

  constructor(protected http: HttpClient) {}

  create(tarifHebergment: ITarifHebergment): Observable<EntityResponseType> {
    return this.http.post<ITarifHebergment>(this.resourceUrl, tarifHebergment, { observe: 'response' });
  }

  update(tarifHebergment: ITarifHebergment): Observable<EntityResponseType> {
    return this.http.put<ITarifHebergment>(this.resourceUrl, tarifHebergment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifHebergment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifHebergment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
