import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TourMediaComponent } from './tour-media.component';
import { TourMediaDetailComponent } from './tour-media-detail.component';
import { TourMediaUpdateComponent } from './tour-media-update.component';
import { TourMediaDeleteDialogComponent } from './tour-media-delete-dialog.component';
import { tourMediaRoute } from './tour-media.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tourMediaRoute)],
  declarations: [TourMediaComponent, TourMediaDetailComponent, TourMediaUpdateComponent, TourMediaDeleteDialogComponent],
  entryComponents: [TourMediaDeleteDialogComponent],
})
export class EcoLifeExpeditionTourMediaModule {}
