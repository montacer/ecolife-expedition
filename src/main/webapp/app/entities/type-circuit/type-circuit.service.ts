import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';

type EntityResponseType = HttpResponse<ITypeCircuit>;
type EntityArrayResponseType = HttpResponse<ITypeCircuit[]>;

@Injectable({ providedIn: 'root' })
export class TypeCircuitService {
  public resourceUrl = SERVER_API_URL + 'api/type-circuits';

  constructor(protected http: HttpClient) {}

  create(typeCircuit: ITypeCircuit): Observable<EntityResponseType> {
    return this.http.post<ITypeCircuit>(this.resourceUrl, typeCircuit, { observe: 'response' });
  }

  update(typeCircuit: ITypeCircuit): Observable<EntityResponseType> {
    return this.http.put<ITypeCircuit>(this.resourceUrl, typeCircuit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeCircuit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeCircuit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
