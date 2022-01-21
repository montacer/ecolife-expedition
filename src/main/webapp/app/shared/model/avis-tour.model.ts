import { IAvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

export interface IAvisTour {
  id?: number;
  author?: string;
  nbreEtoile?: Stars;
  commentaire?: string;
  avisTourMedias?: IAvisTourMedia[];
}

export class AvisTour implements IAvisTour {
  constructor(
    public id?: number,
    public author?: string,
    public nbreEtoile?: Stars,
    public commentaire?: string,
    public avisTourMedias?: IAvisTourMedia[]
  ) {}
}
