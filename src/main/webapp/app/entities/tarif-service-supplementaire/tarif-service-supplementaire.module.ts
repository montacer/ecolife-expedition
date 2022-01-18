import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TarifServiceSupplementaireComponent } from './tarif-service-supplementaire.component';
import { TarifServiceSupplementaireDetailComponent } from './tarif-service-supplementaire-detail.component';
import { TarifServiceSupplementaireUpdateComponent } from './tarif-service-supplementaire-update.component';
import { TarifServiceSupplementaireDeleteDialogComponent } from './tarif-service-supplementaire-delete-dialog.component';
import { tarifServiceSupplementaireRoute } from './tarif-service-supplementaire.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tarifServiceSupplementaireRoute)],
  declarations: [
    TarifServiceSupplementaireComponent,
    TarifServiceSupplementaireDetailComponent,
    TarifServiceSupplementaireUpdateComponent,
    TarifServiceSupplementaireDeleteDialogComponent,
  ],
  entryComponents: [TarifServiceSupplementaireDeleteDialogComponent],
})
export class EcoLifeExpeditionTarifServiceSupplementaireModule {}
