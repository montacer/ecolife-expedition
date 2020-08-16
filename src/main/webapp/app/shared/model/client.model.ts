import { Moment } from 'moment';
import { IReservation } from 'app/shared/model/reservation.model';

export interface IClient {
  id?: number;
  nom?: string;
  prenom?: string;
  adresse?: string;
  email?: string;
  dateNaissance?: Moment;
  reservation?: IReservation;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public adresse?: string,
    public email?: string,
    public dateNaissance?: Moment,
    public reservation?: IReservation
  ) {}
}
