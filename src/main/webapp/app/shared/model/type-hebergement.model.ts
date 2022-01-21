import { IHebergement } from 'app/shared/model/hebergement.model';

export interface ITypeHebergement {
  id?: number;
  libTypeHebergement?: string;
  hebergements?: IHebergement[];
}

export class TypeHebergement implements ITypeHebergement {
  constructor(public id?: number, public libTypeHebergement?: string, public hebergements?: IHebergement[]) {}
}
