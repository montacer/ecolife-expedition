import { IReservation } from 'app/shared/model/reservation.model';
import { IHebergement } from 'app/shared/model/hebergement.model';

export interface IReservationHebergement {
  id?: number;
  montantTotalTTC?: number;
  reservation?: IReservation;
  hebergement?: IHebergement;
}

export class ReservationHebergement implements IReservationHebergement {
  constructor(
    public id?: number,
    public montantTotalTTC?: number,
    public reservation?: IReservation,
    public hebergement?: IHebergement
  ) {}
}
