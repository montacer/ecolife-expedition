import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { ReservationTransfertComponent } from './reservation-transfert.component';
import { ReservationTransfertDetailComponent } from './reservation-transfert-detail.component';
import { ReservationTransfertUpdateComponent } from './reservation-transfert-update.component';
import { ReservationTransfertDeleteDialogComponent } from './reservation-transfert-delete-dialog.component';
import { reservationTransfertRoute } from './reservation-transfert.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(reservationTransfertRoute)],
  declarations: [
    ReservationTransfertComponent,
    ReservationTransfertDetailComponent,
    ReservationTransfertUpdateComponent,
    ReservationTransfertDeleteDialogComponent,
  ],
  entryComponents: [ReservationTransfertDeleteDialogComponent],
})
export class EcoLifeExpeditionReservationTransfertModule {}
