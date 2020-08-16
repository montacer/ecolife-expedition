import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';

type EntityResponseType = HttpResponse<IDonneurOrdre>;
type EntityArrayResponseType = HttpResponse<IDonneurOrdre[]>;

@Injectable({ providedIn: 'root' })
export class DonneurOrdreService {
  public resourceUrl = SERVER_API_URL + 'api/donneur-ordres';

  constructor(protected http: HttpClient) {}

  create(donneurOrdre: IDonneurOrdre): Observable<EntityResponseType> {
    return this.http.post<IDonneurOrdre>(this.resourceUrl, donneurOrdre, { observe: 'response' });
  }

  update(donneurOrdre: IDonneurOrdre): Observable<EntityResponseType> {
    return this.http.put<IDonneurOrdre>(this.resourceUrl, donneurOrdre, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonneurOrdre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonneurOrdre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
