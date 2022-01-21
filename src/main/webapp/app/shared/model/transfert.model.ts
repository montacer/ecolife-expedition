import { IAvisTransfert } from 'app/shared/model/avis-transfert.model';
import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ITypeTransfert } from 'app/shared/model/type-transfert.model';

export interface ITransfert {
  id?: number;
  montantTTC?: number;
  avisTransferts?: IAvisTransfert[];
  reservationTransferts?: IReservationTransfert[];
  typeTransfert?: ITypeTransfert;
}

export class Transfert implements ITransfert {
  constructor(
    public id?: number,
    public montantTTC?: number,
    public avisTransferts?: IAvisTransfert[],
    public reservationTransferts?: IReservationTransfert[],
    public typeTransfert?: ITypeTransfert
  ) {}
}
