import { ITypeTarif } from 'app/shared/model/type-tarif.model';

export interface ITarifServiceSupplementaire {
  id?: number;
  montantTTC?: number;
  typeTarif?: ITypeTarif;
}

export class TarifServiceSupplementaire implements ITarifServiceSupplementaire {
  constructor(public id?: number, public montantTTC?: number, public typeTarif?: ITypeTarif) {}
}
