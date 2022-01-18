import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifServiceSupplementaire, TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';
import { TarifServiceSupplementaireService } from './tarif-service-supplementaire.service';
import { TarifServiceSupplementaireComponent } from './tarif-service-supplementaire.component';
import { TarifServiceSupplementaireDetailComponent } from './tarif-service-supplementaire-detail.component';
import { TarifServiceSupplementaireUpdateComponent } from './tarif-service-supplementaire-update.component';

@Injectable({ providedIn: 'root' })
export class TarifServiceSupplementaireResolve implements Resolve<ITarifServiceSupplementaire> {
  constructor(private service: TarifServiceSupplementaireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifServiceSupplementaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifServiceSupplementaire: HttpResponse<TarifServiceSupplementaire>) => {
          if (tarifServiceSupplementaire.body) {
            return of(tarifServiceSupplementaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifServiceSupplementaire());
  }
}

export const tarifServiceSupplementaireRoute: Routes = [
  {
    path: '',
    component: TarifServiceSupplementaireComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifServiceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarifServiceSupplementaireDetailComponent,
    resolve: {
      tarifServiceSupplementaire: TarifServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifServiceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarifServiceSupplementaireUpdateComponent,
    resolve: {
      tarifServiceSupplementaire: TarifServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifServiceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarifServiceSupplementaireUpdateComponent,
    resolve: {
      tarifServiceSupplementaire: TarifServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifServiceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
