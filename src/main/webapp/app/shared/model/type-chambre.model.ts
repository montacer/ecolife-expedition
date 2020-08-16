import { IChambre } from 'app/shared/model/chambre.model';

export interface ITypeChambre {
  id?: number;
  libelleTypeChambre?: string;
  chambres?: IChambre[];
}

export class TypeChambre implements ITypeChambre {
  constructor(public id?: number, public libelleTypeChambre?: string, public chambres?: IChambre[]) {}
}
