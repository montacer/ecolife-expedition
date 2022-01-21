import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TypeTransfertComponent } from './type-transfert.component';
import { TypeTransfertDetailComponent } from './type-transfert-detail.component';
import { TypeTransfertUpdateComponent } from './type-transfert-update.component';
import { TypeTransfertDeleteDialogComponent } from './type-transfert-delete-dialog.component';
import { typeTransfertRoute } from './type-transfert.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(typeTransfertRoute)],
  declarations: [TypeTransfertComponent, TypeTransfertDetailComponent, TypeTransfertUpdateComponent, TypeTransfertDeleteDialogComponent],
  entryComponents: [TypeTransfertDeleteDialogComponent],
})
export class EcoLifeExpeditionTypeTransfertModule {}
