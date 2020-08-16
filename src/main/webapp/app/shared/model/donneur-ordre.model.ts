import { IReservation } from 'app/shared/model/reservation.model';

export interface IDonneurOrdre {
  id?: number;
  nom?: string;
  prenom?: string;
  adresse?: string;
  numTelephone?: string;
  email?: string;
  reservations?: IReservation[];
}

export class DonneurOrdre implements IDonneurOrdre {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public adresse?: string,
    public numTelephone?: string,
    public email?: string,
    public reservations?: IReservation[]
  ) {}
}
