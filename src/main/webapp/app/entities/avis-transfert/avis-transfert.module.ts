import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { AvisTransfertComponent } from './avis-transfert.component';
import { AvisTransfertDetailComponent } from './avis-transfert-detail.component';
import { AvisTransfertUpdateComponent } from './avis-transfert-update.component';
import { AvisTransfertDeleteDialogComponent } from './avis-transfert-delete-dialog.component';
import { avisTransfertRoute } from './avis-transfert.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(avisTransfertRoute)],
  declarations: [AvisTransfertComponent, AvisTransfertDetailComponent, AvisTransfertUpdateComponent, AvisTransfertDeleteDialogComponent],
  entryComponents: [AvisTransfertDeleteDialogComponent],
})
export class EcoLifeExpeditionAvisTransfertModule {}
