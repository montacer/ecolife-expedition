import { ITour } from 'app/shared/model/tour.model';

export interface ITypeCircuit {
  id?: number;
  libTypeCircuit?: string;
  tours?: ITour[];
}

export class TypeCircuit implements ITypeCircuit {
  constructor(public id?: number, public libTypeCircuit?: string, public tours?: ITour[]) {}
}
