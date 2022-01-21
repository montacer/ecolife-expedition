import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReservationTour } from 'app/shared/model/reservation-tour.model';
import { ReservationTourService } from './reservation-tour.service';

@Component({
  templateUrl: './reservation-tour-delete-dialog.component.html',
})
export class ReservationTourDeleteDialogComponent {
  reservationTour?: IReservationTour;

  constructor(
    protected reservationTourService: ReservationTourService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reservationTourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reservationTourListModification');
      this.activeModal.close();
    });
  }
}
