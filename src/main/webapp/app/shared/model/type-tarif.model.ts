import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';
import { ITarifHebergement } from 'app/shared/model/tarif-hebergement.model';

export interface ITypeTarif {
  id?: number;
  libTypeTarif?: string;
  tarifTransferts?: ITarifTransfert[];
  tarifHebergements?: ITarifHebergement[];
}

export class TypeTarif implements ITypeTarif {
  constructor(
    public id?: number,
    public libTypeTarif?: string,
    public tarifTransferts?: ITarifTransfert[],
    public tarifHebergements?: ITarifHebergement[]
  ) {}
}
