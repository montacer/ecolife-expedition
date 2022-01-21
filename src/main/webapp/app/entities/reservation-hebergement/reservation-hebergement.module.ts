import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { ReservationHebergementComponent } from './reservation-hebergement.component';
import { ReservationHebergementDetailComponent } from './reservation-hebergement-detail.component';
import { ReservationHebergementUpdateComponent } from './reservation-hebergement-update.component';
import { ReservationHebergementDeleteDialogComponent } from './reservation-hebergement-delete-dialog.component';
import { reservationHebergementRoute } from './reservation-hebergement.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(reservationHebergementRoute)],
  declarations: [
    ReservationHebergementComponent,
    ReservationHebergementDetailComponent,
    ReservationHebergementUpdateComponent,
    ReservationHebergementDeleteDialogComponent,
  ],
  entryComponents: [ReservationHebergementDeleteDialogComponent],
})
export class EcoLifeExpeditionReservationHebergementModule {}
