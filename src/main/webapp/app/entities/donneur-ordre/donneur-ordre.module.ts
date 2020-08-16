import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { DonneurOrdreComponent } from './donneur-ordre.component';
import { DonneurOrdreDetailComponent } from './donneur-ordre-detail.component';
import { DonneurOrdreUpdateComponent } from './donneur-ordre-update.component';
import { DonneurOrdreDeleteDialogComponent } from './donneur-ordre-delete-dialog.component';
import { donneurOrdreRoute } from './donneur-ordre.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(donneurOrdreRoute)],
  declarations: [DonneurOrdreComponent, DonneurOrdreDetailComponent, DonneurOrdreUpdateComponent, DonneurOrdreDeleteDialogComponent],
  entryComponents: [DonneurOrdreDeleteDialogComponent],
})
export class EcoLifeExpeditionDonneurOrdreModule {}
