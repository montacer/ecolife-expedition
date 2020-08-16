import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TypeCircuitComponent } from './type-circuit.component';
import { TypeCircuitDetailComponent } from './type-circuit-detail.component';
import { TypeCircuitUpdateComponent } from './type-circuit-update.component';
import { TypeCircuitDeleteDialogComponent } from './type-circuit-delete-dialog.component';
import { typeCircuitRoute } from './type-circuit.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(typeCircuitRoute)],
  declarations: [TypeCircuitComponent, TypeCircuitDetailComponent, TypeCircuitUpdateComponent, TypeCircuitDeleteDialogComponent],
  entryComponents: [TypeCircuitDeleteDialogComponent],
})
export class EcoLifeExpeditionTypeCircuitModule {}
