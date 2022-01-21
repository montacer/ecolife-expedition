import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TarifTransfertComponent } from './tarif-transfert.component';
import { TarifTransfertDetailComponent } from './tarif-transfert-detail.component';
import { TarifTransfertUpdateComponent } from './tarif-transfert-update.component';
import { TarifTransfertDeleteDialogComponent } from './tarif-transfert-delete-dialog.component';
import { tarifTransfertRoute } from './tarif-transfert.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tarifTransfertRoute)],
  declarations: [
    TarifTransfertComponent,
    TarifTransfertDetailComponent,
    TarifTransfertUpdateComponent,
    TarifTransfertDeleteDialogComponent,
  ],
  entryComponents: [TarifTransfertDeleteDialogComponent],
})
export class EcoLifeExpeditionTarifTransfertModule {}
