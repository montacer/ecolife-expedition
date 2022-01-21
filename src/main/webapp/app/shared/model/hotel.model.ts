import { IChambre } from 'app/shared/model/chambre.model';
import { IHotelMedia } from 'app/shared/model/hotel-media.model';
import { IAgence } from 'app/shared/model/agence.model';

export interface IHotel {
  id?: number;
  libelleHotel?: string;
  classeHotel?: number;
  adresse?: string;
  lattitude?: number;
  longitude?: number;
  chambres?: IChambre[];
  hotelMedias?: IHotelMedia[];
  agence?: IAgence;
}

export class Hotel implements IHotel {
  constructor(
    public id?: number,
    public libelleHotel?: string,
    public classeHotel?: number,
    public adresse?: string,
    public lattitude?: number,
    public longitude?: number,
    public chambres?: IChambre[],
    public hotelMedias?: IHotelMedia[],
    public agence?: IAgence
  ) {}
}
