import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHebergement, Hebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';
import { HebergementComponent } from './hebergement.component';
import { HebergementDetailComponent } from './hebergement-detail.component';
import { HebergementUpdateComponent } from './hebergement-update.component';

@Injectable({ providedIn: 'root' })
export class HebergementResolve implements Resolve<IHebergement> {
  constructor(private service: HebergementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHebergement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hebergement: HttpResponse<Hebergement>) => {
          if (hebergement.body) {
            return of(hebergement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hebergement());
  }
}

export const hebergementRoute: Routes = [
  {
    path: '',
    component: HebergementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HebergementDetailComponent,
    resolve: {
      hebergement: HebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HebergementUpdateComponent,
    resolve: {
      hebergement: HebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HebergementUpdateComponent,
    resolve: {
      hebergement: HebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
