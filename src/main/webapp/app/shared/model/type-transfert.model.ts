import { ITransfert } from 'app/shared/model/transfert.model';
import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';

export interface ITypeTransfert {
  id?: number;
  libTypeTransfert?: string;
  transferts?: ITransfert[];
  tarifTransferts?: ITarifTransfert[];
}

export class TypeTransfert implements ITypeTransfert {
  constructor(
    public id?: number,
    public libTypeTransfert?: string,
    public transferts?: ITransfert[],
    public tarifTransferts?: ITarifTransfert[]
  ) {}
}
