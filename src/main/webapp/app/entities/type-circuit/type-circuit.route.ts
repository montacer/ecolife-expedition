import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeCircuit, TypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from './type-circuit.service';
import { TypeCircuitComponent } from './type-circuit.component';
import { TypeCircuitDetailComponent } from './type-circuit-detail.component';
import { TypeCircuitUpdateComponent } from './type-circuit-update.component';

@Injectable({ providedIn: 'root' })
export class TypeCircuitResolve implements Resolve<ITypeCircuit> {
  constructor(private service: TypeCircuitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeCircuit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeCircuit: HttpResponse<TypeCircuit>) => {
          if (typeCircuit.body) {
            return of(typeCircuit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeCircuit());
  }
}

export const typeCircuitRoute: Routes = [
  {
    path: '',
    component: TypeCircuitComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeCircuit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeCircuitDetailComponent,
    resolve: {
      typeCircuit: TypeCircuitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeCircuit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeCircuitUpdateComponent,
    resolve: {
      typeCircuit: TypeCircuitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeCircuit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeCircuitUpdateComponent,
    resolve: {
      typeCircuit: TypeCircuitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeCircuit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
