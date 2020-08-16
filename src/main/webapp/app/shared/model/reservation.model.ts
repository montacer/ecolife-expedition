import { Moment } from 'moment';
import { IChambre } from 'app/shared/model/chambre.model';
import { IClient } from 'app/shared/model/client.model';
import { ITour } from 'app/shared/model/tour.model';
import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';

export interface IReservation {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  nombreAdulte?: number;
  nombreJeune?: number;
  nombreEnfant?: number;
  montantTTC?: number;
  libDevise?: string;
  chambres?: IChambre[];
  clients?: IClient[];
  tour?: ITour;
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
    public montantTTC?: number,
    public libDevise?: string,
    public chambres?: IChambre[],
    public clients?: IClient[],
    public tour?: ITour,
    public donneurOrdre?: IDonneurOrdre
  ) {}
}
