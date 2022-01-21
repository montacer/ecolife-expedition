import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifHebergment, TarifHebergment } from 'app/shared/model/tarif-hebergment.model';
import { TarifHebergmentService } from './tarif-hebergment.service';
import { TarifHebergmentComponent } from './tarif-hebergment.component';
import { TarifHebergmentDetailComponent } from './tarif-hebergment-detail.component';
import { TarifHebergmentUpdateComponent } from './tarif-hebergment-update.component';

@Injectable({ providedIn: 'root' })
export class TarifHebergmentResolve implements Resolve<ITarifHebergment> {
  constructor(private service: TarifHebergmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifHebergment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifHebergment: HttpResponse<TarifHebergment>) => {
          if (tarifHebergment.body) {
            return of(tarifHebergment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifHebergment());
  }
}

export const tarifHebergmentRoute: Routes = [
  {
    path: '',
    component: TarifHebergmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarifHebergmentDetailComponent,
    resolve: {
      tarifHebergment: TarifHebergmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarifHebergmentUpdateComponent,
    resolve: {
      tarifHebergment: TarifHebergmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarifHebergmentUpdateComponent,
    resolve: {
      tarifHebergment: TarifHebergmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifHebergment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
