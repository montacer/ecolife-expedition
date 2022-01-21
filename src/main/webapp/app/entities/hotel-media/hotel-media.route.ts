import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHotelMedia, HotelMedia } from 'app/shared/model/hotel-media.model';
import { HotelMediaService } from './hotel-media.service';
import { HotelMediaComponent } from './hotel-media.component';
import { HotelMediaDetailComponent } from './hotel-media-detail.component';
import { HotelMediaUpdateComponent } from './hotel-media-update.component';

@Injectable({ providedIn: 'root' })
export class HotelMediaResolve implements Resolve<IHotelMedia> {
  constructor(private service: HotelMediaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelMedia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hotelMedia: HttpResponse<HotelMedia>) => {
          if (hotelMedia.body) {
            return of(hotelMedia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HotelMedia());
  }
}

export const hotelMediaRoute: Routes = [
  {
    path: '',
    component: HotelMediaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotelMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelMediaDetailComponent,
    resolve: {
      hotelMedia: HotelMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotelMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelMediaUpdateComponent,
    resolve: {
      hotelMedia: HotelMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotelMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelMediaUpdateComponent,
    resolve: {
      hotelMedia: HotelMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.hotelMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
