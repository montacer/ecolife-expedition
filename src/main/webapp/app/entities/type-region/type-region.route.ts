import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeRegion, TypeRegion } from 'app/shared/model/type-region.model';
import { TypeRegionService } from './type-region.service';
import { TypeRegionComponent } from './type-region.component';
import { TypeRegionDetailComponent } from './type-region-detail.component';
import { TypeRegionUpdateComponent } from './type-region-update.component';

@Injectable({ providedIn: 'root' })
export class TypeRegionResolve implements Resolve<ITypeRegion> {
  constructor(private service: TypeRegionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeRegion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeRegion: HttpResponse<TypeRegion>) => {
          if (typeRegion.body) {
            return of(typeRegion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeRegion());
  }
}

export const typeRegionRoute: Routes = [
  {
    path: '',
    component: TypeRegionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeRegion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeRegionDetailComponent,
    resolve: {
      typeRegion: TypeRegionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeRegion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeRegionUpdateComponent,
    resolve: {
      typeRegion: TypeRegionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeRegion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeRegionUpdateComponent,
    resolve: {
      typeRegion: TypeRegionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.typeRegion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
