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
      {
        path: 'tarif-transfert',
        loadChildren: () => import('./tarif-transfert/tarif-transfert.module').then(m => m.EcoLifeExpeditionTarifTransfertModule),
      },
      {
        path: 'type-transfert',
        loadChildren: () => import('./type-transfert/type-transfert.module').then(m => m.EcoLifeExpeditionTypeTransfertModule),
      },
      {
        path: 'transfert',
        loadChildren: () => import('./transfert/transfert.module').then(m => m.EcoLifeExpeditionTransfertModule),
      },
      {
        path: 'avis-transfert',
        loadChildren: () => import('./avis-transfert/avis-transfert.module').then(m => m.EcoLifeExpeditionAvisTransfertModule),
      },
      {
        path: 'reservation-tour',
        loadChildren: () => import('./reservation-tour/reservation-tour.module').then(m => m.EcoLifeExpeditionReservationTourModule),
      },
      {
        path: 'reservation-hebergement',
        loadChildren: () =>
          import('./reservation-hebergement/reservation-hebergement.module').then(m => m.EcoLifeExpeditionReservationHebergementModule),
      },
      {
        path: 'reservation-transfert',
        loadChildren: () =>
          import('./reservation-transfert/reservation-transfert.module').then(m => m.EcoLifeExpeditionReservationTransfertModule),
      },
      {
        path: 'tarif-hebergment',
        loadChildren: () => import('./tarif-hebergment/tarif-hebergment.module').then(m => m.EcoLifeExpeditionTarifHebergmentModule),
      },
      {
        path: 'type-hebergement',
        loadChildren: () => import('./type-hebergement/type-hebergement.module').then(m => m.EcoLifeExpeditionTypeHebergementModule),
      },
      {
        path: 'hebergement',
        loadChildren: () => import('./hebergement/hebergement.module').then(m => m.EcoLifeExpeditionHebergementModule),
      },
      {
        path: 'hotel-media',
        loadChildren: () => import('./hotel-media/hotel-media.module').then(m => m.EcoLifeExpeditionHotelMediaModule),
      },
      {
        path: 'tarif-hebergement',
        loadChildren: () => import('./tarif-hebergement/tarif-hebergement.module').then(m => m.EcoLifeExpeditionTarifHebergementModule),
      },
      {
        path: 'avis-tour',
        loadChildren: () => import('./avis-tour/avis-tour.module').then(m => m.EcoLifeExpeditionAvisTourModule),
      },
      {
        path: 'avis-tour-media',
        loadChildren: () => import('./avis-tour-media/avis-tour-media.module').then(m => m.EcoLifeExpeditionAvisTourMediaModule),
      },
      {
        path: 'avis-hebergement',
        loadChildren: () => import('./avis-hebergement/avis-hebergement.module').then(m => m.EcoLifeExpeditionAvisHebergementModule),
      },
      {
        path: 'avis-hebergement-media',
        loadChildren: () =>
          import('./avis-hebergement-media/avis-hebergement-media.module').then(m => m.EcoLifeExpeditionAvisHebergementMediaModule),
      },
      {
        path: 'type-region',
        loadChildren: () => import('./type-region/type-region.module').then(m => m.EcoLifeExpeditionTypeRegionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EcoLifeExpeditionEntityModule {}
