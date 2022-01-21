import { IAvisTour } from 'app/shared/model/avis-tour.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';
import { Stars } from 'app/shared/model/enumerations/stars.model';

export interface IAvisTourMedia {
  id?: number;
  mediaType?: MediaType;
  mediaUrl?: string;
  mediacontentContentType?: string;
  mediacontent?: any;
  score?: Stars;
  avisTour?: IAvisTour;
}

export class AvisTourMedia implements IAvisTourMedia {
  constructor(
    public id?: number,
    public mediaType?: MediaType,
    public mediaUrl?: string,
    public mediacontentContentType?: string,
    public mediacontent?: any,
    public score?: Stars,
    public avisTour?: IAvisTour
  ) {}
}
