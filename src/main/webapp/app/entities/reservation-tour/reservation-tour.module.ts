import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { ReservationTourComponent } from './reservation-tour.component';
import { ReservationTourDetailComponent } from './reservation-tour-detail.component';
import { ReservationTourUpdateComponent } from './reservation-tour-update.component';
import { ReservationTourDeleteDialogComponent } from './reservation-tour-delete-dialog.component';
import { reservationTourRoute } from './reservation-tour.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(reservationTourRoute)],
  declarations: [
    ReservationTourComponent,
    ReservationTourDetailComponent,
    ReservationTourUpdateComponent,
    ReservationTourDeleteDialogComponent,
  ],
  entryComponents: [ReservationTourDeleteDialogComponent],
})
export class EcoLifeExpeditionReservationTourModule {}
