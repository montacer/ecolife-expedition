import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { ReservationHebergementService } from './reservation-hebergement.service';

@Component({
  templateUrl: './reservation-hebergement-delete-dialog.component.html',
})
export class ReservationHebergementDeleteDialogComponent {
  reservationHebergement?: IReservationHebergement;

  constructor(
    protected reservationHebergementService: ReservationHebergementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reservationHebergementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reservationHebergementListModification');
      this.activeModal.close();
    });
  }
}
