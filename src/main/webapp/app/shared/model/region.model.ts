import { ITour } from 'app/shared/model/tour.model';
import { IPays } from 'app/shared/model/pays.model';

export interface IRegion {
  id?: number;
  libRegion?: string;
  tours?: ITour[];
  pays?: IPays;
}

export class Region implements IRegion {
  constructor(public id?: number, public libRegion?: string, public tours?: ITour[], public pays?: IPays) {}
}
