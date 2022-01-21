import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeRegion } from 'app/shared/model/type-region.model';

type EntityResponseType = HttpResponse<ITypeRegion>;
type EntityArrayResponseType = HttpResponse<ITypeRegion[]>;

@Injectable({ providedIn: 'root' })
export class TypeRegionService {
  public resourceUrl = SERVER_API_URL + 'api/type-regions';

  constructor(protected http: HttpClient) {}

  create(typeRegion: ITypeRegion): Observable<EntityResponseType> {
    return this.http.post<ITypeRegion>(this.resourceUrl, typeRegion, { observe: 'response' });
  }

  update(typeRegion: ITypeRegion): Observable<EntityResponseType> {
    return this.http.put<ITypeRegion>(this.resourceUrl, typeRegion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeRegion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
