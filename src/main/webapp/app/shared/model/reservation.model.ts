import { Moment } from 'moment';
import { IReservationTour } from 'app/shared/model/reservation-tour.model';
import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { IChambre } from 'app/shared/model/chambre.model';
import { IClient } from 'app/shared/model/client.model';
import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';

export interface IReservation {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  nombreAdulte?: number;
  nombreJeune?: number;
  nombreEnfant?: number;
  montantTotalTTC?: number;
  libDevise?: string;
  reservationTour?: IReservationTour;
  reservationHebergement?: IReservationHebergement;
  reservationTransfert?: IReservationTransfert;
  chambres?: IChambre[];
  clients?: IClient[];
  donneurOrdre?: IDonneurOrdre;
}

export class Reservation implements IReservation {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public nombreAdulte?: number,
    public nombreJeune?: number,
    public nombreEnfant?: number,
    public montantTotalTTC?: number,
    public libDevise?: string,
    public reservationTour?: IReservationTour,
    public reservationHebergement?: IReservationHebergement,
    public reservationTransfert?: IReservationTransfert,
    public chambres?: IChambre[],
    public clients?: IClient[],
    public donneurOrdre?: IDonneurOrdre
  ) {}
}
