import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TarifHebergementComponent } from './tarif-hebergement.component';
import { TarifHebergementDetailComponent } from './tarif-hebergement-detail.component';
import { TarifHebergementUpdateComponent } from './tarif-hebergement-update.component';
import { TarifHebergementDeleteDialogComponent } from './tarif-hebergement-delete-dialog.component';
import { tarifHebergementRoute } from './tarif-hebergement.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tarifHebergementRoute)],
  declarations: [
    TarifHebergementComponent,
    TarifHebergementDetailComponent,
    TarifHebergementUpdateComponent,
    TarifHebergementDeleteDialogComponent,
  ],
  entryComponents: [TarifHebergementDeleteDialogComponent],
})
export class EcoLifeExpeditionTarifHebergementModule {}
