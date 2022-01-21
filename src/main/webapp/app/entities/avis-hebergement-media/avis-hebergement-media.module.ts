import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { AvisHebergementMediaComponent } from './avis-hebergement-media.component';
import { AvisHebergementMediaDetailComponent } from './avis-hebergement-media-detail.component';
import { AvisHebergementMediaUpdateComponent } from './avis-hebergement-media-update.component';
import { AvisHebergementMediaDeleteDialogComponent } from './avis-hebergement-media-delete-dialog.component';
import { avisHebergementMediaRoute } from './avis-hebergement-media.route';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild(avisHebergementMediaRoute)],
  declarations: [
    AvisHebergementMediaComponent,
    AvisHebergementMediaDetailComponent,
    AvisHebergementMediaUpdateComponent,
    AvisHebergementMediaDeleteDialogComponent,
  ],
  entryComponents: [AvisHebergementMediaDeleteDialogComponent],
})
export class EcoLifeExpeditionAvisHebergementMediaModule {}
