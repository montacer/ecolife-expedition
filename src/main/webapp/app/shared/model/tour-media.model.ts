import { ITour } from 'app/shared/model/tour.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface ITourMedia {
  id?: number;
  mediaUrl?: string;
  mediaType?: MediaType;
  mediacontentContentType?: string;
  mediacontent?: any;
  tour?: ITour;
}

export class TourMedia implements ITourMedia {
  constructor(
    public id?: number,
    public mediaUrl?: string,
    public mediaType?: MediaType,
    public mediacontentContentType?: string,
    public mediacontent?: any,
    public tour?: ITour
  ) {}
}
