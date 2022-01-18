import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TarifTourComponent } from './tarif-tour.component';
import { TarifTourDetailComponent } from './tarif-tour-detail.component';
import { TarifTourUpdateComponent } from './tarif-tour-update.component';
import { TarifTourDeleteDialogComponent } from './tarif-tour-delete-dialog.component';
import { tarifTourRoute } from './tarif-tour.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(tarifTourRoute)],
  declarations: [TarifTourComponent, TarifTourDetailComponent, TarifTourUpdateComponent, TarifTourDeleteDialogComponent],
  entryComponents: [TarifTourDeleteDialogComponent],
})
export class EcoLifeExpeditionTarifTourModule {}
