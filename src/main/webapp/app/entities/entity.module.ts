import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pays',
        loadChildren: () => import('./pays/pays.module').then(m => m.EcoLifeExpeditionPaysModule),
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.EcoLifeExpeditionRegionModule),
      },
      {
        path: 'type-circuit',
        loadChildren: () => import('./type-circuit/type-circuit.module').then(m => m.EcoLifeExpeditionTypeCircuitModule),
      },
      {
        path: 'tour',
        loadChildren: () => import('./tour/tour.module').then(m => m.EcoLifeExpeditionTourModule),
      },
      {
        path: 'service-supplementaire',
        loadChildren: () =>
          import('./service-supplementaire/service-supplementaire.module').then(m => m.EcoLifeExpeditionServiceSupplementaireModule),
      },
      {
        path: 'agence',
        loadChildren: () => import('./agence/agence.module').then(m => m.EcoLifeExpeditionAgenceModule),
      },
      {
        path: 'donneur-ordre',
        loadChildren: () => import('./donneur-ordre/donneur-ordre.module').then(m => m.EcoLifeExpeditionDonneurOrdreModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.EcoLifeExpeditionClientModule),
      },
      {
        path: 'reservation',
        loadChildren: () => import('./reservation/reservation.module').then(m => m.EcoLifeExpeditionReservationModule),
      },
      {
        path: 'hotel',
        loadChildren: () => import('./hotel/hotel.module').then(m => m.EcoLifeExpeditionHotelModule),
      },
      {
        path: 'chambre',
        loadChildren: () => import('./chambre/chambre.module').then(m => m.EcoLifeExpeditionChambreModule),
      },
      {
        path: 'type-chambre',
        loadChildren: () => import('./type-chambre/type-chambre.module').then(m => m.EcoLifeExpeditionTypeChambreModule),
      },
      {
        path: 'type-tarif',
        loadChildren: () => import('./type-tarif/type-tarif.module').then(m => m.EcoLifeExpeditionTypeTarifModule),
      },
      {
        path: 'tour-media',
        loadChildren: () => import('./tour-media/tour-media.module').then(m => m.EcoLifeExpeditionTourMediaModule),
      },
      {
        path: 'tarif-tour',
        loadChildren: () => import('./tarif-tour/tarif-tour.module').then(m => m.EcoLifeExpeditionTarifTourModule),
      },
      {
        path: 'tarif-service-supplementaire',
        loadChildren: () =>
          import('./tarif-service-supplementaire/tarif-service-supplementaire.module').then(
            m => m.EcoLifeExpeditionTarifServiceSupplementaireModule
          ),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EcoLifeExpeditionEntityModule {}
