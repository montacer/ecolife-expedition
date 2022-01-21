import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisTour, AvisTour } from 'app/shared/model/avis-tour.model';
import { AvisTourService } from './avis-tour.service';
import { AvisTourComponent } from './avis-tour.component';
import { AvisTourDetailComponent } from './avis-tour-detail.component';
import { AvisTourUpdateComponent } from './avis-tour-update.component';

@Injectable({ providedIn: 'root' })
export class AvisTourResolve implements Resolve<IAvisTour> {
  constructor(private service: AvisTourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisTour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisTour: HttpResponse<AvisTour>) => {
          if (avisTour.body) {
            return of(avisTour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisTour());
  }
}

export const avisTourRoute: Routes = [
  {
    path: '',
    component: AvisTourComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisTourDetailComponent,
    resolve: {
      avisTour: AvisTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisTourUpdateComponent,
    resolve: {
      avisTour: AvisTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisTourUpdateComponent,
    resolve: {
      avisTour: AvisTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
