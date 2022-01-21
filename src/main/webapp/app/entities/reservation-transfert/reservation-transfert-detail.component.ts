import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';

@Component({
  selector: 'jhi-reservation-transfert-detail',
  templateUrl: './reservation-transfert-detail.component.html',
})
export class ReservationTransfertDetailComponent implements OnInit {
  reservationTransfert: IReservationTransfert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationTransfert }) => (this.reservationTransfert = reservationTransfert));
  }

  previousState(): void {
    window.history.back();
  }
}
