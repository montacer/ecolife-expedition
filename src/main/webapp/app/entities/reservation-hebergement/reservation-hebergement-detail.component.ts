import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';

@Component({
  selector: 'jhi-reservation-hebergement-detail',
  templateUrl: './reservation-hebergement-detail.component.html',
})
export class ReservationHebergementDetailComponent implements OnInit {
  reservationHebergement: IReservationHebergement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationHebergement }) => (this.reservationHebergement = reservationHebergement));
  }

  previousState(): void {
    window.history.back();
  }
}
