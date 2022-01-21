import { IReservation } from 'app/shared/model/reservation.model';
import { ITransfert } from 'app/shared/model/transfert.model';

export interface IReservationTransfert {
  id?: number;
  montantTotalTTC?: number;
  reservation?: IReservation;
  transfert?: ITransfert;
}

export class ReservationTransfert implements IReservationTransfert {
  constructor(
    public id?: number,
    public montantTotalTTC?: number,
    public reservation?: IReservation,
    public transfert?: ITransfert
  ) {}
}
