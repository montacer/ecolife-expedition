import { ITourMedia } from 'app/shared/model/tour-media.model';
import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { IRegion } from 'app/shared/model/region.model';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { Saison } from 'app/shared/model/enumerations/saison.model';
import { TourStatus } from 'app/shared/model/enumerations/tour-status.model';

export interface ITour {
  id?: number;
  libTitre?: string;
  imageUrl?: string;
  videoUrl?: string;
  imageContentContentType?: string;
  imageContent?: any;
  videoContentContentType?: string;
  videoContent?: any;
  conseil?: string;
  prixTTC?: number;
  description?: string;
  remise?: boolean;
  prixRemise?: number;
  starScore?: number;
  duree?: number;
  saison?: Saison;
  status?: TourStatus;
  tourMedias?: ITourMedia[];
  serviceSupplementaires?: IServiceSupplementaire[];
  region?: IRegion;
  typeCircuit?: ITypeCircuit;
}

export class Tour implements ITour {
  constructor(
    public id?: number,
    public libTitre?: string,
    public imageUrl?: string,
    public videoUrl?: string,
    public imageContentContentType?: string,
    public imageContent?: any,
    public videoContentContentType?: string,
    public videoContent?: any,
    public conseil?: string,
    public prixTTC?: number,
    public description?: string,
    public remise?: boolean,
    public prixRemise?: number,
    public starScore?: number,
    public duree?: number,
    public saison?: Saison,
    public status?: TourStatus,
    public tourMedias?: ITourMedia[],
    public serviceSupplementaires?: IServiceSupplementaire[],
    public region?: IRegion,
    public typeCircuit?: ITypeCircuit
  ) {
    this.remise = this.remise || false;
  }
}
