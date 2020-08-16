import { IChambre } from 'app/shared/model/chambre.model';
import { IAgence } from 'app/shared/model/agence.model';

export interface IHotel {
  id?: number;
  libelleHotel?: string;
  classeHotel?: number;
  chambres?: IChambre[];
  agence?: IAgence;
}

export class Hotel implements IHotel {
  constructor(
    public id?: number,
    public libelleHotel?: string,
    public classeHotel?: number,
    public chambres?: IChambre[],
    public agence?: IAgence
  ) {}
}
