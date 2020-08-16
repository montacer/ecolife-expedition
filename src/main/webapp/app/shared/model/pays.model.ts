import { IRegion } from 'app/shared/model/region.model';

export interface IPays {
  id?: number;
  codIsoPays?: string;
  libPays?: string;
  codeDevisePays?: string;
  regions?: IRegion[];
}

export class Pays implements IPays {
  constructor(
    public id?: number,
    public codIsoPays?: string,
    public libPays?: string,
    public codeDevisePays?: string,
    public regions?: IRegion[]
  ) {}
}
