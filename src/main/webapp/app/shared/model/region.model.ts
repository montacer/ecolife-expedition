import { IPays } from 'app/shared/model/pays.model';
import { ITypeRegion } from 'app/shared/model/type-region.model';

export interface IRegion {
  id?: number;
  libRegion?: string;
  listPays?: IPays[];
  pays?: IPays;
  typeRegion?: ITypeRegion;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public libRegion?: string,
    public listPays?: IPays[],
    public pays?: IPays,
    public typeRegion?: ITypeRegion
  ) {}
}
