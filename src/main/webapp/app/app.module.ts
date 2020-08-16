import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EcoLifeExpeditionSharedModule } from 'app/shared/shared.module';
import { EcoLifeExpeditionCoreModule } from 'app/core/core.module';
import { EcoLifeExpeditionAppRoutingModule } from './app-routing.module';
import { EcoLifeExpeditionHomeModule } from './home/home.module';
import { EcoLifeExpeditionEntityModule } from './entities/entity.module';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    EcoLifeExpeditionSharedModule,
    EcoLifeExpeditionCoreModule,
    EcoLifeExpeditionHomeModule,
    EcoLifeExpeditionEntityModule,
    EcoLifeExpeditionAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class EcoLifeExpeditionAppModule {}
