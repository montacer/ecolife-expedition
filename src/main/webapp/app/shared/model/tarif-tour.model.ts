import { ITypeTarif } from 'app/shared/model/type-tarif.model';

export interface ITarifTour {
  id?: number;
  montantTTC?: number;
  typeTarif?: ITypeTarif;
}

export class TarifTour implements ITarifTour {
  constructor(public id?: number, public montantTTC?: number, public typeTarif?: ITypeTarif) {}
}
