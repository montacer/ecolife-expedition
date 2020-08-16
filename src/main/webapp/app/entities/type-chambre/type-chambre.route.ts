import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeChambre, TypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';
import { TypeChambreComponent } from './type-chambre.component';
import { TypeChambreDetailComponent } from './type-chambre-detail.component';
import { TypeChambreUpdateComponent } from './type-chambre-update.component';

@Injectable({ providedIn: 'root' })
export class TypeChambreResolve implements Resolve<ITypeChambre> {
  constructor(private service: TypeChambreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeChambre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeChambre: HttpResponse<TypeChambre>) => {
          if (typeChambre.body) {
            return of(typeChambre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeChambre());
  }
}

export const typeChambreRoute: Routes = [
  {
    path: '',
    component: TypeChambreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeChambre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeChambreDetailComponent,
    resolve: {
      typeChambre: TypeChambreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeChambre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeChambreUpdateComponent,
    resolve: {
      typeChambre: TypeChambreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeChambre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeChambreUpdateComponent,
    resolve: {
      typeChambre: TypeChambreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeChambre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
