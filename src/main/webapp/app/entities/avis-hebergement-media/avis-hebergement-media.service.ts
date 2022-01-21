import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';

type EntityResponseType = HttpResponse<IAvisHebergementMedia>;
type EntityArrayResponseType = HttpResponse<IAvisHebergementMedia[]>;

@Injectable({ providedIn: 'root' })
export class AvisHebergementMediaService {
  public resourceUrl = SERVER_API_URL + 'api/avis-hebergement-medias';

  constructor(protected http: HttpClient) {}

  create(avisHebergementMedia: IAvisHebergementMedia): Observable<EntityResponseType> {
    return this.http.post<IAvisHebergementMedia>(this.resourceUrl, avisHebergementMedia, { observe: 'response' });
  }

  update(avisHebergementMedia: IAvisHebergementMedia): Observable<EntityResponseType> {
    return this.http.put<IAvisHebergementMedia>(this.resourceUrl, avisHebergementMedia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvisHebergementMedia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvisHebergementMedia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
