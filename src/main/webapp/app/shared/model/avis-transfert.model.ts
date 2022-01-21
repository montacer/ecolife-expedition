import { ITransfert } from 'app/shared/model/transfert.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

export interface IAvisTransfert {
  id?: number;
  author?: string;
  nbreEtoile?: Stars;
  commentaire?: string;
  transfert?: ITransfert;
}

export class AvisTransfert implements IAvisTransfert {
  constructor(
    public id?: number,
    public author?: string,
    public nbreEtoile?: Stars,
    public commentaire?: string,
    public transfert?: ITransfert
  ) {}
}
