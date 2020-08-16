import { IHotel } from 'app/shared/model/hotel.model';

export interface IAgence {
  id?: number;
  libAgence?: string;
  adresseAgence?: string;
  contactAgence?: string;
  hotels?: IHotel[];
}

export class Agence implements IAgence {
  constructor(
    public id?: number,
    public libAgence?: string,
    public adresseAgence?: string,
    public contactAgence?: string,
    public hotels?: IHotel[]
  ) {}
}
