import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TypeHebergementComponent } from './type-hebergement.component';
import { TypeHebergementDetailComponent } from './type-hebergement-detail.component';
import { TypeHebergementUpdateComponent } from './type-hebergement-update.component';
import { TypeHebergementDeleteDialogComponent } from './type-hebergement-delete-dialog.component';
import { typeHebergementRoute } from './type-hebergement.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(typeHebergementRoute)],
  declarations: [
    TypeHebergementComponent,
    TypeHebergementDetailComponent,
    TypeHebergementUpdateComponent,
    TypeHebergementDeleteDialogComponent,
  ],
  entryComponents: [TypeHebergementDeleteDialogComponent],
})
export class EcoLifeExpeditionTypeHebergementModule {}
