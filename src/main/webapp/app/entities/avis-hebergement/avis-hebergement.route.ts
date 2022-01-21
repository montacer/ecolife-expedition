import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisHebergement, AvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { AvisHebergementService } from './avis-hebergement.service';
import { AvisHebergementComponent } from './avis-hebergement.component';
import { AvisHebergementDetailComponent } from './avis-hebergement-detail.component';
import { AvisHebergementUpdateComponent } from './avis-hebergement-update.component';

@Injectable({ providedIn: 'root' })
export class AvisHebergementResolve implements Resolve<IAvisHebergement> {
  constructor(private service: AvisHebergementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisHebergement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisHebergement: HttpResponse<AvisHebergement>) => {
          if (avisHebergement.body) {
            return of(avisHebergement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisHebergement());
  }
}

export const avisHebergementRoute: Routes = [
  {
    path: '',
    component: AvisHebergementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisHebergementDetailComponent,
    resolve: {
      avisHebergement: AvisHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisHebergementUpdateComponent,
    resolve: {
      avisHebergement: AvisHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisHebergementUpdateComponent,
    resolve: {
      avisHebergement: AvisHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
