import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisTourMedia, AvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { AvisTourMediaService } from './avis-tour-media.service';
import { AvisTourMediaComponent } from './avis-tour-media.component';
import { AvisTourMediaDetailComponent } from './avis-tour-media-detail.component';
import { AvisTourMediaUpdateComponent } from './avis-tour-media-update.component';

@Injectable({ providedIn: 'root' })
export class AvisTourMediaResolve implements Resolve<IAvisTourMedia> {
  constructor(private service: AvisTourMediaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisTourMedia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisTourMedia: HttpResponse<AvisTourMedia>) => {
          if (avisTourMedia.body) {
            return of(avisTourMedia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisTourMedia());
  }
}

export const avisTourMediaRoute: Routes = [
  {
    path: '',
    component: AvisTourMediaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTourMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisTourMediaDetailComponent,
    resolve: {
      avisTourMedia: AvisTourMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTourMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisTourMediaUpdateComponent,
    resolve: {
      avisTourMedia: AvisTourMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTourMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisTourMediaUpdateComponent,
    resolve: {
      avisTourMedia: AvisTourMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisTourMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
