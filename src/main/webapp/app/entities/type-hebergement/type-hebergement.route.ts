import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeHebergement, TypeHebergement } from 'app/shared/model/type-hebergement.model';
import { TypeHebergementService } from './type-hebergement.service';
import { TypeHebergementComponent } from './type-hebergement.component';
import { TypeHebergementDetailComponent } from './type-hebergement-detail.component';
import { TypeHebergementUpdateComponent } from './type-hebergement-update.component';

@Injectable({ providedIn: 'root' })
export class TypeHebergementResolve implements Resolve<ITypeHebergement> {
  constructor(private service: TypeHebergementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeHebergement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeHebergement: HttpResponse<TypeHebergement>) => {
          if (typeHebergement.body) {
            return of(typeHebergement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeHebergement());
  }
}

export const typeHebergementRoute: Routes = [
  {
    path: '',
    component: TypeHebergementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeHebergementDetailComponent,
    resolve: {
      typeHebergement: TypeHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeHebergementUpdateComponent,
    resolve: {
      typeHebergement: TypeHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeHebergementUpdateComponent,
    resolve: {
      typeHebergement: TypeHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
