import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChambre } from 'app/shared/model/chambre.model';

type EntityResponseType = HttpResponse<IChambre>;
type EntityArrayResponseType = HttpResponse<IChambre[]>;

@Injectable({ providedIn: 'root' })
export class ChambreService {
  public resourceUrl = SERVER_API_URL + 'api/chambres';

  constructor(protected http: HttpClient) {}

  create(chambre: IChambre): Observable<EntityResponseType> {
    return this.http.post<IChambre>(this.resourceUrl, chambre, { observe: 'response' });
  }

  update(chambre: IChambre): Observable<EntityResponseType> {
    return this.http.put<IChambre>(this.resourceUrl, chambre, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChambre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChambre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
