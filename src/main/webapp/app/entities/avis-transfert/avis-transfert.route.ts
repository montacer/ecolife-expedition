import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisTransfert, AvisTransfert } from 'app/shared/model/avis-transfert.model';
import { AvisTransfertService } from './avis-transfert.service';
import { AvisTransfertComponent } from './avis-transfert.component';
import { AvisTransfertDetailComponent } from './avis-transfert-detail.component';
import { AvisTransfertUpdateComponent } from './avis-transfert-update.component';

@Injectable({ providedIn: 'root' })
export class AvisTransfertResolve implements Resolve<IAvisTransfert> {
  constructor(private service: AvisTransfertService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisTransfert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisTransfert: HttpResponse<AvisTransfert>) => {
          if (avisTransfert.body) {
            return of(avisTransfert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisTransfert());
  }
}

export const avisTransfertRoute: Routes = [
  {
    path: '',
    component: AvisTransfertComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisTransfertDetailComponent,
    resolve: {
      avisTransfert: AvisTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisTransfertUpdateComponent,
    resolve: {
      avisTransfert: AvisTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisTransfertUpdateComponent,
    resolve: {
      avisTransfert: AvisTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
