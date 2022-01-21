import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReservationTransfert, ReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ReservationTransfertService } from './reservation-transfert.service';
import { ReservationTransfertComponent } from './reservation-transfert.component';
import { ReservationTransfertDetailComponent } from './reservation-transfert-detail.component';
import { ReservationTransfertUpdateComponent } from './reservation-transfert-update.component';

@Injectable({ providedIn: 'root' })
export class ReservationTransfertResolve implements Resolve<IReservationTransfert> {
  constructor(private service: ReservationTransfertService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReservationTransfert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reservationTransfert: HttpResponse<ReservationTransfert>) => {
          if (reservationTransfert.body) {
            return of(reservationTransfert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReservationTransfert());
  }
}

export const reservationTransfertRoute: Routes = [
  {
    path: '',
    component: ReservationTransfertComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReservationTransfertDetailComponent,
    resolve: {
      reservationTransfert: ReservationTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReservationTransfertUpdateComponent,
    resolve: {
      reservationTransfert: ReservationTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReservationTransfertUpdateComponent,
    resolve: {
      reservationTransfert: ReservationTransfertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.reservationTransfert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
