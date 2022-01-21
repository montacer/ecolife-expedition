import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisHebergementMedia, AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { AvisHebergementMediaService } from './avis-hebergement-media.service';
import { AvisHebergementMediaComponent } from './avis-hebergement-media.component';
import { AvisHebergementMediaDetailComponent } from './avis-hebergement-media-detail.component';
import { AvisHebergementMediaUpdateComponent } from './avis-hebergement-media-update.component';

@Injectable({ providedIn: 'root' })
export class AvisHebergementMediaResolve implements Resolve<IAvisHebergementMedia> {
  constructor(private service: AvisHebergementMediaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisHebergementMedia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisHebergementMedia: HttpResponse<AvisHebergementMedia>) => {
          if (avisHebergementMedia.body) {
            return of(avisHebergementMedia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisHebergementMedia());
  }
}

export const avisHebergementMediaRoute: Routes = [
  {
    path: '',
    component: AvisHebergementMediaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergementMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisHebergementMediaDetailComponent,
    resolve: {
      avisHebergementMedia: AvisHebergementMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergementMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisHebergementMediaUpdateComponent,
    resolve: {
      avisHebergementMedia: AvisHebergementMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergementMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisHebergementMediaUpdateComponent,
    resolve: {
      avisHebergementMedia: AvisHebergementMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.avisHebergementMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
