import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { HotelComponent } from './hotel.component';
import { HotelDetailComponent } from './hotel-detail.component';
import { HotelUpdateComponent } from './hotel-update.component';
import { HotelDeleteDialogComponent } from './hotel-delete-dialog.component';
import { hotelRoute } from './hotel.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(hotelRoute)],
  declarations: [HotelComponent, HotelDetailComponent, HotelUpdateComponent, HotelDeleteDialogComponent],
  entryComponents: [HotelDeleteDialogComponent],
})
export class EcoLifeExpeditionHotelModule {}
