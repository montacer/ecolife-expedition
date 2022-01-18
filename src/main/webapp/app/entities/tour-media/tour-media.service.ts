import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITourMedia } from 'app/shared/model/tour-media.model';

type EntityResponseType = HttpResponse<ITourMedia>;
type EntityArrayResponseType = HttpResponse<ITourMedia[]>;

@Injectable({ providedIn: 'root' })
export class TourMediaService {
  public resourceUrl = SERVER_API_URL + 'api/tour-medias';

  constructor(protected http: HttpClient) {}

  create(tourMedia: ITourMedia): Observable<EntityResponseType> {
    return this.http.post<ITourMedia>(this.resourceUrl, tourMedia, { observe: 'response' });
  }

  update(tourMedia: ITourMedia): Observable<EntityResponseType> {
    return this.http.put<ITourMedia>(this.resourceUrl, tourMedia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITourMedia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITourMedia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
