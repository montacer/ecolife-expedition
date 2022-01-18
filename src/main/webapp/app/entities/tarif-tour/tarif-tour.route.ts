import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifTour, TarifTour } from 'app/shared/model/tarif-tour.model';
import { TarifTourService } from './tarif-tour.service';
import { TarifTourComponent } from './tarif-tour.component';
import { TarifTourDetailComponent } from './tarif-tour-detail.component';
import { TarifTourUpdateComponent } from './tarif-tour-update.component';

@Injectable({ providedIn: 'root' })
export class TarifTourResolve implements Resolve<ITarifTour> {
  constructor(private service: TarifTourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifTour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifTour: HttpResponse<TarifTour>) => {
          if (tarifTour.body) {
            return of(tarifTour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifTour());
  }
}

export const tarifTourRoute: Routes = [
  {
    path: '',
    component: TarifTourComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TarifTourDetailComponent,
    resolve: {
      tarifTour: TarifTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TarifTourUpdateComponent,
    resolve: {
      tarifTour: TarifTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TarifTourUpdateComponent,
    resolve: {
      tarifTour: TarifTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.tarifTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
