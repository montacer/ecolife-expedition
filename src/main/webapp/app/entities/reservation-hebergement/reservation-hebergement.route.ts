import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReservationHebergement, ReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { ReservationHebergementService } from './reservation-hebergement.service';
import { ReservationHebergementComponent } from './reservation-hebergement.component';
import { ReservationHebergementDetailComponent } from './reservation-hebergement-detail.component';
import { ReservationHebergementUpdateComponent } from './reservation-hebergement-update.component';

@Injectable({ providedIn: 'root' })
export class ReservationHebergementResolve implements Resolve<IReservationHebergement> {
  constructor(private service: ReservationHebergementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReservationHebergement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reservationHebergement: HttpResponse<ReservationHebergement>) => {
          if (reservationHebergement.body) {
            return of(reservationHebergement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReservationHebergement());
  }
}

export const reservationHebergementRoute: Routes = [
  {
    path: '',
    component: ReservationHebergementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReservationHebergementDetailComponent,
    resolve: {
      reservationHebergement: ReservationHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReservationHebergementUpdateComponent,
    resolve: {
      reservationHebergement: ReservationHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReservationHebergementUpdateComponent,
    resolve: {
      reservationHebergement: ReservationHebergementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationHebergement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
