import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TarifHebergmentComponent } from './tarif-hebergment.component';
import { TarifHebergmentDetailComponent } from './tarif-hebergment-detail.component';
import { TarifHebergmentUpdateComponent } from './tarif-hebergment-update.component';
import { TarifHebergmentDeleteDialogComponent } from './tarif-hebergment-delete-dialog.component';
import { tarifHebergmentRoute } from './tarif-hebergment.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tarifHebergmentRoute)],
  declarations: [
    TarifHebergmentComponent,
    TarifHebergmentDetailComponent,
    TarifHebergmentUpdateComponent,
    TarifHebergmentDeleteDialogComponent,
  ],
  entryComponents: [TarifHebergmentDeleteDialogComponent],
})
export class EcoLifeExpeditionTarifHebergmentModule {}
