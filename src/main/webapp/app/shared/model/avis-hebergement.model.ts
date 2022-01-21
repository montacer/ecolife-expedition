import { IAvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { IHebergement } from 'app/shared/model/hebergement.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

export interface IAvisHebergement {
  id?: number;
  author?: string;
  nbreEtoile?: Stars;
  commentaire?: string;
  avisHebergementMedias?: IAvisHebergementMedia[];
  hebergement?: IHebergement;
}

export class AvisHebergement implements IAvisHebergement {
  constructor(
    public id?: number,
    public author?: string,
    public nbreEtoile?: Stars,
    public commentaire?: string,
    public avisHebergementMedias?: IAvisHebergementMedia[],
    public hebergement?: IHebergement
  ) {}
}
