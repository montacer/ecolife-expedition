import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisTourMedia } from 'app/shared/model/avis-tour-media.model';

type EntityResponseType = HttpResponse<IAvisTourMedia>;
type EntityArrayResponseType = HttpResponse<IAvisTourMedia[]>;

@Injectable({ providedIn: 'root' })
export class AvisTourMediaService {
  public resourceUrl = SERVER_API_URL + 'api/avis-tour-medias';

  constructor(protected http: HttpClient) {}

  create(avisTourMedia: IAvisTourMedia): Observable<EntityResponseType> {
    return this.http.post<IAvisTourMedia>(this.resourceUrl, avisTourMedia, { observe: 'response' });
  }

  update(avisTourMedia: IAvisTourMedia): Observable<EntityResponseType> {
    return this.http.put<IAvisTourMedia>(this.resourceUrl, avisTourMedia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvisTourMedia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvisTourMedia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
