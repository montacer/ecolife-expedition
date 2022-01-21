import { IHotel } from 'app/shared/model/hotel.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface IHotelMedia {
  id?: number;
  mediaUrl?: string;
  mediaType?: MediaType;
  mediacontentContentType?: string;
  mediacontent?: any;
  hotel?: IHotel;
}

export class HotelMedia implements IHotelMedia {
  constructor(
    public id?: number,
    public mediaUrl?: string,
    public mediaType?: MediaType,
    public mediacontentContentType?: string,
    public mediacontent?: any,
    public hotel?: IHotel
  ) {}
}
