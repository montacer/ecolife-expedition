import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { HotelMediaComponent } from './hotel-media.component';
import { HotelMediaDetailComponent } from './hotel-media-detail.component';
import { HotelMediaUpdateComponent } from './hotel-media-update.component';
import { HotelMediaDeleteDialogComponent } from './hotel-media-delete-dialog.component';
import { hotelMediaRoute } from './hotel-media.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(hotelMediaRoute)],
  declarations: [HotelMediaComponent, HotelMediaDetailComponent, HotelMediaUpdateComponent, HotelMediaDeleteDialogComponent],
  entryComponents: [HotelMediaDeleteDialogComponent],
})
export class EcoLifeExpeditionHotelMediaModule {}
