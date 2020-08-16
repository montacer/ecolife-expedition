import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotel } from 'app/shared/model/hotel.model';

@Component({
  selector: 'jhi-hotel-detail',
  templateUrl: './hotel-detail.component.html',
})
export class HotelDetailComponent implements OnInit {
  hotel: IHotel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotel }) => (this.hotel = hotel));
  }

  previousState(): void {
    window.history.back();
  }
}
