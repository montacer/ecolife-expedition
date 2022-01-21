import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { TourShowComponent } from './tour-show/tour-show.component';

@NgModule({
  imports: [EcoLifeExpeditionSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, TourShowComponent],
})
export class EcoLifeExpeditionHomeModule {}
