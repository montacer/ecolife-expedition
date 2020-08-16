import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { ServiceSupplementaireComponent } from './service-supplementaire.component';
import { ServiceSupplementaireDetailComponent } from './service-supplementaire-detail.component';
import { ServiceSupplementaireUpdateComponent } from './service-supplementaire-update.component';
import { ServiceSupplementaireDeleteDialogComponent } from './service-supplementaire-delete-dialog.component';
import { serviceSupplementaireRoute } from './service-supplementaire.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(serviceSupplementaireRoute)],
  declarations: [
    ServiceSupplementaireComponent,
    ServiceSupplementaireDetailComponent,
    ServiceSupplementaireUpdateComponent,
    ServiceSupplementaireDeleteDialogComponent,
  ],
  entryComponents: [ServiceSupplementaireDeleteDialogComponent],
})
export class EcoLifeExpeditionServiceSupplementaireModule {}
