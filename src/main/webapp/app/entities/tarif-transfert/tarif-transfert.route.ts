import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifTransfert, TarifTransfert } from 'app/shared/model/tarif-transfert.model';
import { TarifTransfertService } from './tarif-transfert.service';
import { TarifTransfertComponent } from './tarif-transfert.component';
import { TarifTransfertDetailComponent } from './tarif-transfert-detail.component';
import { TarifTransfertUpdateComponent } from './tarif-transfert-update.component';

@Injectable({ providedIn: 'root' })
export class TarifTransfertResolve implements Resolve<ITarifTransfert> {
  constructor(private service: TarifTransfertService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifTransfert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifTransfert: HttpResponse<TarifTransfert>) => {
          if (tarifTransfert.body) {
            return of(tarifTransfert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifTransfert());
  }
}

export const tarifTransfertRoute: Routes = [
  {
    path: '',
    component: TarifTransfertComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarifTransfertDetailComponent,
    resolve: {
      tarifTransfert: TarifTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarifTransfertUpdateComponent,
    resolve: {
      tarifTransfert: TarifTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarifTransfertUpdateComponent,
    resolve: {
      tarifTransfert: TarifTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
