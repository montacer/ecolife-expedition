import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReservationTour, ReservationTour } from 'app/shared/model/reservation-tour.model';
import { ReservationTourService } from './reservation-tour.service';
import { ReservationTourComponent } from './reservation-tour.component';
import { ReservationTourDetailComponent } from './reservation-tour-detail.component';
import { ReservationTourUpdateComponent } from './reservation-tour-update.component';

@Injectable({ providedIn: 'root' })
export class ReservationTourResolve implements Resolve<IReservationTour> {
  constructor(private service: ReservationTourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReservationTour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reservationTour: HttpResponse<ReservationTour>) => {
          if (reservationTour.body) {
            return of(reservationTour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReservationTour());
  }
}

export const reservationTourRoute: Routes = [
  {
    path: '',
    component: ReservationTourComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReservationTourDetailComponent,
    resolve: {
      reservationTour: ReservationTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReservationTourUpdateComponent,
    resolve: {
      reservationTour: ReservationTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReservationTourUpdateComponent,
    resolve: {
      reservationTour: ReservationTourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
