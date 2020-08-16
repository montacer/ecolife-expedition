import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDonneurOrdre, DonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from './donneur-ordre.service';
import { DonneurOrdreComponent } from './donneur-ordre.component';
import { DonneurOrdreDetailComponent } from './donneur-ordre-detail.component';
import { DonneurOrdreUpdateComponent } from './donneur-ordre-update.component';

@Injectable({ providedIn: 'root' })
export class DonneurOrdreResolve implements Resolve<IDonneurOrdre> {
  constructor(private service: DonneurOrdreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonneurOrdre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((donneurOrdre: HttpResponse<DonneurOrdre>) => {
          if (donneurOrdre.body) {
            return of(donneurOrdre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonneurOrdre());
  }
}

export const donneurOrdreRoute: Routes = [
  {
    path: '',
    component: DonneurOrdreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.donneurOrdre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonneurOrdreDetailComponent,
    resolve: {
      donneurOrdre: DonneurOrdreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.donneurOrdre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonneurOrdreUpdateComponent,
    resolve: {
      donneurOrdre: DonneurOrdreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.donneurOrdre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonneurOrdreUpdateComponent,
    resolve: {
      donneurOrdre: DonneurOrdreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecoLifeExpeditionApp.donneurOrdre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
