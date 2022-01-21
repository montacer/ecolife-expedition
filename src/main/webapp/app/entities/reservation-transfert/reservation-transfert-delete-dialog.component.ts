import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ReservationTransfertService } from './reservation-transfert.service';

@Component({
  templateUrl: './reservation-transfert-delete-dialog.component.html',
})
export class ReservationTransfertDeleteDialogComponent {
  reservationTransfert?: IReservationTransfert;

  constructor(
    protected reservationTransfertService: ReservationTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reservationTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reservationTransfertListModification');
      this.activeModal.close();
    });
  }
}
