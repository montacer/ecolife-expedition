import { ITypeTransfert } from 'app/shared/model/type-transfert.model';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';

export interface ITarifTransfert {
  id?: number;
  montantTTC?: number;
  typeTransfert?: ITypeTransfert;
  typeTarif?: ITypeTarif;
}

export class TarifTransfert implements ITarifTransfert {
  constructor(public id?: number, public montantTTC?: number, public typeTransfert?: ITypeTransfert, public typeTarif?: ITypeTarif) {}
}
