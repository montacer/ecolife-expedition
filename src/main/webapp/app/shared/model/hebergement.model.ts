import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';

export interface IHebergement {
  id?: number;
  description?: string;
  adresse?: string;
  lattitude?: number;
  longitude?: number;
  montantTTc?: number;
  reservationHebergements?: IReservationHebergement[];
  avisHebergements?: IAvisHebergement[];
  typeHebergement?: ITypeHebergement;
}

export class Hebergement implements IHebergement {
  constructor(
    public id?: number,
    public description?: string,
    public adresse?: string,
    public lattitude?: number,
    public longitude?: number,
    public montantTTc?: number,
    public reservationHebergements?: IReservationHebergement[],
    public avisHebergements?: IAvisHebergement[],
    public typeHebergement?: ITypeHebergement
  ) {}
}
