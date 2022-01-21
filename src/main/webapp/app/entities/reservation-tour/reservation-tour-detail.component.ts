import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReservationTour } from 'app/shared/model/reservation-tour.model';

@Component({
  selector: 'jhi-reservation-tour-detail',
  templateUrl: './reservation-tour-detail.component.html',
})
export class ReservationTourDetailComponent implements OnInit {
  reservationTour: IReservationTour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationTour }) => (this.reservationTour = reservationTour));
  }

  previousState(): void {
    window.history.back();
  }
}
