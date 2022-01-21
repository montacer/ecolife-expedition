import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { AvisHebergementComponent } from './avis-hebergement.component';
import { AvisHebergementDetailComponent } from './avis-hebergement-detail.component';
import { AvisHebergementUpdateComponent } from './avis-hebergement-update.component';
import { AvisHebergementDeleteDialogComponent } from './avis-hebergement-delete-dialog.component';
import { avisHebergementRoute } from './avis-hebergement.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(avisHebergementRoute)],
  declarations: [
    AvisHebergementComponent,
    AvisHebergementDetailComponent,
    AvisHebergementUpdateComponent,
    AvisHebergementDeleteDialogComponent,
  ],
  entryComponents: [AvisHebergementDeleteDialogComponent],
})
export class EcoLifeExpeditionAvisHebergementModule {}
