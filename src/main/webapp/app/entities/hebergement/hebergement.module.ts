import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { HebergementComponent } from './hebergement.component';
import { HebergementDetailComponent } from './hebergement-detail.component';
import { HebergementUpdateComponent } from './hebergement-update.component';
import { HebergementDeleteDialogComponent } from './hebergement-delete-dialog.component';
import { hebergementRoute } from './hebergement.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(hebergementRoute)],
  declarations: [HebergementComponent, HebergementDetailComponent, HebergementUpdateComponent, HebergementDeleteDialogComponent],
  entryComponents: [HebergementDeleteDialogComponent],
})
export class EcoLifeExpeditionHebergementModule {}
