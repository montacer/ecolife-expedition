import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegion } from 'app/shared/model/region.model';
import { TypeCircuit } from 'app/shared/model/type-circuit.model'; 

type EntityResponseType = HttpResponse<IRegion>;
type EntityArrayResponseType = HttpResponse<IRegion[]>;

@Injectable({ providedIn: 'root' })
export class HomeFilterService {
	
  public resourceUrl = SERVER_API_URL + 'filter';

  constructor(protected http: HttpClient) {}

  
  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  queryForRegion(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegion[]>(this.resourceUrl + "/regions", { params: options, observe: 'response' });
  }

  queryForTypeCircuit(req?: any): Observable<HttpResponse<TypeCircuit>> {
    const options = createRequestOption(req);
    return this.http.get<TypeCircuit[]>(this.resourceUrl + "/typecircuits", { params: options, observe: 'response' });
  }

}
