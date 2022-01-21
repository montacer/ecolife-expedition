import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { TypeRegionComponent } from './type-region.component';
import { TypeRegionDetailComponent } from './type-region-detail.component';
import { TypeRegionUpdateComponent } from './type-region-update.component';
import { TypeRegionDeleteDialogComponent } from './type-region-delete-dialog.component';
import { typeRegionRoute } from './type-region.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(typeRegionRoute)],
  declarations: [TypeRegionComponent, TypeRegionDetailComponent, TypeRegionUpdateComponent, TypeRegionDeleteDialogComponent],
  entryComponents: [TypeRegionDeleteDialogComponent],
})
export class EcoLifeExpeditionTypeRegionModule {}
