import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { ChambreComponent } from './chambre.component';
import { ChambreDetailComponent } from './chambre-detail.component';
import { ChambreUpdateComponent } from './chambre-update.component';
import { ChambreDeleteDialogComponent } from './chambre-delete-dialog.component';
import { chambreRoute } from './chambre.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(chambreRoute)],
  declarations: [ChambreComponent, ChambreDetailComponent, ChambreUpdateComponent, ChambreDeleteDialogComponent],
  entryComponents: [ChambreDeleteDialogComponent],
})
export class EcoLifeExpeditionChambreModule {}
