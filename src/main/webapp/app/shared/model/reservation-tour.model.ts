import { IReservation } from 'app/shared/model/reservation.model';
import { ITour } from 'app/shared/model/tour.model';

export interface IReservationTour {
  id?: number;
  montantTotalTTC?: number;
  reservation?: IReservation;
  tour?: ITour;
}

export class ReservationTour implements IReservationTour {
  constructor(public id?: number, public montantTotalTTC?: number, public reservation?: IReservation, public tour?: ITour) {}
}
