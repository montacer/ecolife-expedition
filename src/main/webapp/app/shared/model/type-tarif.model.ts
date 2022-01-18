import { ITarifTour } from 'app/shared/model/tarif-tour.model';
import { ITarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

export interface ITypeTarif {
  id?: number;
  libTypeTarif?: string;
  tarifTours?: ITarifTour[];
  tarifServiceSupplementaires?: ITarifServiceSupplementaire[];
}

export class TypeTarif implements ITypeTarif {
  constructor(
    public id?: number,
    public libTypeTarif?: string,
    public tarifTours?: ITarifTour[],
    public tarifServiceSupplementaires?: ITarifServiceSupplementaire[]
  ) {}
}
