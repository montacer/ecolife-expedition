import { ITour } from 'app/shared/model/tour.model';

export interface IServiceSupplementaire {
  id?: number;
  libelleService?: string;
  tour?: ITour;
}

export class ServiceSupplementaire implements IServiceSupplementaire {
  constructor(public id?: number, public libelleService?: string, public tour?: ITour) {}
}
