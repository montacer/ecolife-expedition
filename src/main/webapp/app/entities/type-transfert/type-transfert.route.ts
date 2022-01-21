import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeTransfert, TypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from './type-transfert.service';
import { TypeTransfertComponent } from './type-transfert.component';
import { TypeTransfertDetailComponent } from './type-transfert-detail.component';
import { TypeTransfertUpdateComponent } from './type-transfert-update.component';

@Injectable({ providedIn: 'root' })
export class TypeTransfertResolve implements Resolve<ITypeTransfert> {
  constructor(private service: TypeTransfertService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeTransfert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeTransfert: HttpResponse<TypeTransfert>) => {
          if (typeTransfert.body) {
            return of(typeTransfert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeTransfert());
  }
}

export const typeTransfertRoute: Routes = [
  {
    path: '',
    component: TypeTransfertComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeTransfertDetailComponent,
    resolve: {
      typeTransfert: TypeTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeTransfertUpdateComponent,
    resolve: {
      typeTransfert: TypeTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeTransfertUpdateComponent,
    resolve: {
      typeTransfert: TypeTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
