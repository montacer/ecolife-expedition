import { IRegion } from 'app/shared/model/region.model';

export interface ITypeRegion {
  id?: number;
  libTypeRegion?: string;
  regions?: IRegion[];
}

export class TypeRegion implements ITypeRegion {
  constructor(public id?: number, public libTypeRegion?: string, public regions?: IRegion[]) {}
}
