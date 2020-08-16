import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TypeChambreComponent } from './type-chambre.component';
import { TypeChambreDetailComponent } from './type-chambre-detail.component';
import { TypeChambreUpdateComponent } from './type-chambre-update.component';
import { TypeChambreDeleteDialogComponent } from './type-chambre-delete-dialog.component';
import { typeChambreRoute } from './type-chambre.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(typeChambreRoute)],
  declarations: [TypeChambreComponent, TypeChambreDetailComponent, TypeChambreUpdateComponent, TypeChambreDeleteDialogComponent],
  entryComponents: [TypeChambreDeleteDialogComponent],
})
export class EcoLifeExpeditionTypeChambreModule {}
