import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHotel, Hotel } from 'app/shared/model/hotel.model';
import { HotelService } from './hotel.service';
import { HotelComponent } from './hotel.component';
import { HotelDetailComponent } from './hotel-detail.component';
import { HotelUpdateComponent } from './hotel-update.component';

@Injectable({ providedIn: 'root' })
export class HotelResolve implements Resolve<IHotel> {
  constructor(private service: HotelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hotel: HttpResponse<Hotel>) => {
          if (hotel.body) {
            return of(hotel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hotel());
  }
}

export const hotelRoute: Routes = [
  {
    path: '',
    component: HotelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelDetailComponent,
    resolve: {
      hotel: HotelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelUpdateComponent,
    resolve: {
      hotel: HotelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelUpdateComponent,
    resolve: {
      hotel: HotelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
