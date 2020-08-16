import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceSupplementaire, ServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { ServiceSupplementaireService } from './service-supplementaire.service';
import { ServiceSupplementaireComponent } from './service-supplementaire.component';
import { ServiceSupplementaireDetailComponent } from './service-supplementaire-detail.component';
import { ServiceSupplementaireUpdateComponent } from './service-supplementaire-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceSupplementaireResolve implements Resolve<IServiceSupplementaire> {
  constructor(private service: ServiceSupplementaireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceSupplementaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceSupplementaire: HttpResponse<ServiceSupplementaire>) => {
          if (serviceSupplementaire.body) {
            return of(serviceSupplementaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceSupplementaire());
  }
}

export const serviceSupplementaireRoute: Routes = [
  {
    path: '',
    component: ServiceSupplementaireComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.serviceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceSupplementaireDetailComponent,
    resolve: {
      serviceSupplementaire: ServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.serviceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceSupplementaireUpdateComponent,
    resolve: {
      serviceSupplementaire: ServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.serviceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceSupplementaireUpdateComponent,
    resolve: {
      serviceSupplementaire: ServiceSupplementaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.serviceSupplementaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
