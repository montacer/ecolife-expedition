import { ITypeTarif } from 'app/shared/model/type-tarif.model';

export interface ITarifHebergement {
  id?: number;
  montantTTC?: number;
  typeTarif?: ITypeTarif;
}

export class TarifHebergement implements ITarifHebergement {
  constructor(public id?: number, public montantTTC?: number, public typeTarif?: ITypeTarif) {}
}
