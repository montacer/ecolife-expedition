import { IReservation } from 'app/shared/model/reservation.model';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { IHotel } from 'app/shared/model/hotel.model';

export interface IChambre {
  id?: number;
  prixTTC?: number;
  reservation?: IReservation;
  typeChambre?: ITypeChambre;
  hotel?: IHotel;
}

export class Chambre implements IChambre {
  constructor(
    public id?: number,
    public prixTTC?: number,
    public reservation?: IReservation,
    public typeChambre?: ITypeChambre,
    public hotel?: IHotel
  ) {}
}
