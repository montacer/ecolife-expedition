import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifHebergement } from 'app/shared/model/tarif-hebergement.model';

type EntityResponseType = HttpResponse<ITarifHebergement>;
type EntityArrayResponseType = HttpResponse<ITarifHebergement[]>;

@Injectable({ providedIn: 'root' })
export class TarifHebergementService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-hebergements';

  constructor(protected http: HttpClient) {}

  create(tarifHebergement: ITarifHebergement): Observable<EntityResponseType> {
    return this.http.post<ITarifHebergement>(this.resourceUrl, tarifHebergement, { observe: 'response' });
  }

  update(tarifHebergement: ITarifHebergement): Observable<EntityResponseType> {
    return this.http.put<ITarifHebergement>(this.resourceUrl, tarifHebergement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifHebergement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifHebergement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
