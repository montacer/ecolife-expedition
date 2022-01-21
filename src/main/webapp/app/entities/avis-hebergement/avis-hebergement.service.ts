import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';

type EntityResponseType = HttpResponse<IAvisHebergement>;
type EntityArrayResponseType = HttpResponse<IAvisHebergement[]>;

@Injectable({ providedIn: 'root' })
export class AvisHebergementService {
  public resourceUrl = SERVER_API_URL + 'api/avis-hebergements';

  constructor(protected http: HttpClient) {}

  create(avisHebergement: IAvisHebergement): Observable<EntityResponseType> {
    return this.http.post<IAvisHebergement>(this.resourceUrl, avisHebergement, { observe: 'response' });
  }

  update(avisHebergement: IAvisHebergement): Observable<EntityResponseType> {
    return this.http.put<IAvisHebergement>(this.resourceUrl, avisHebergement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvisHebergement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvisHebergement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
