import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface IAvisHebergementMedia {
  id?: number;
  mediaType?: MediaType;
  mediaUrl?: string;
  mediacontentContentType?: string;
  mediacontent?: any;
  avisHebergement?: IAvisHebergement;
}

export class AvisHebergementMedia implements IAvisHebergementMedia {
  constructor(
    public id?: number,
    public mediaType?: MediaType,
    public mediaUrl?: string,
    public mediacontentContentType?: string,
    public mediacontent?: any,
    public avisHebergement?: IAvisHebergement
  ) {}
}
