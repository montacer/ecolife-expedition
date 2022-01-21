import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifHebergement, TarifHebergement } from 'app/shared/model/tarif-hebergement.model';
import { TarifHebergementService } from './tarif-hebergement.service';
import { TarifHebergementComponent } from './tarif-hebergement.component';
import { TarifHebergementDetailComponent } from './tarif-hebergement-detail.component';
import { TarifHebergementUpdateComponent } from './tarif-hebergement-update.component';

@Injectable({ providedIn: 'root' })
export class TarifHebergementResolve implements Resolve<ITarifHebergement> {
  constructor(private service: TarifHebergementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifHebergement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifHebergement: HttpResponse<TarifHebergement>) => {
          if (tarifHebergement.body) {
            return of(tarifHebergement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifHebergement());
  }
}

export const tarifHebergementRoute: Routes = [
  {
    path: '',
    component: TarifHebergementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarifHebergementDetailComponent,
    resolve: {
      tarifHebergement: TarifHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarifHebergementUpdateComponent,
    resolve: {
      tarifHebergement: TarifHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarifHebergementUpdateComponent,
    resolve: {
      tarifHebergement: TarifHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
