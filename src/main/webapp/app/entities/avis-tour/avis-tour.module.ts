import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { AvisTourComponent } from './avis-tour.component';
import { AvisTourDetailComponent } from './avis-tour-detail.component';
import { AvisTourUpdateComponent } from './avis-tour-update.component';
import { AvisTourDeleteDialogComponent } from './avis-tour-delete-dialog.component';
import { avisTourRoute } from './avis-tour.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(avisTourRoute)],
  declarations: [AvisTourComponent, AvisTourDetailComponent, AvisTourUpdateComponent, AvisTourDeleteDialogComponent],
  entryComponents: [AvisTourDeleteDialogComponent],
})
export class EcoLifeExpeditionAvisTourModule {}
