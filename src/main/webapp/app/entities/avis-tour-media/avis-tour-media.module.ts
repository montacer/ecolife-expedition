import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { AvisTourMediaComponent } from './avis-tour-media.component';
import { AvisTourMediaDetailComponent } from './avis-tour-media-detail.component';
import { AvisTourMediaUpdateComponent } from './avis-tour-media-update.component';
import { AvisTourMediaDeleteDialogComponent } from './avis-tour-media-delete-dialog.component';
import { avisTourMediaRoute } from './avis-tour-media.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(avisTourMediaRoute)],
  declarations: [AvisTourMediaComponent, AvisTourMediaDetailComponent, AvisTourMediaUpdateComponent, AvisTourMediaDeleteDialogComponent],
  entryComponents: [AvisTourMediaDeleteDialogComponent],
})
export class EcoLifeExpeditionAvisTourMediaModule {}
