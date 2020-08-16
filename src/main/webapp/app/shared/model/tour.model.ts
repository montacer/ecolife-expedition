import { IReservation } from 'app/shared/model/reservation.model';
import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { IRegion } from 'app/shared/model/region.model';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';

export interface ITour {
  id?: number;
  libTitre?: string;
  imageUrl?: string;
  videoUrl?: string;
  conseil?: string;
  prixTTC?: number;
  reservation?: IReservation;
  serviceSupplementaires?: IServiceSupplementaire[];
  region?: IRegion;
  typeCircuit?: ITypeCircuit;
}

export class Tour implements ITour {
  constructor(
    public id?: number,
    public libTitre?: string,
    public imageUrl?: string,
    public videoUrl?: string,
    public conseil?: string,
    public prixTTC?: number,
    public reservation?: IReservation,
    public serviceSupplementaires?: IServiceSupplementaire[],
    public region?: IRegion,
    public typeCircuit?: ITypeCircuit
  ) {}
}
