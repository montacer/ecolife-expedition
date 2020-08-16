import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from './hotel.service';

@Component({
  templateUrl: './hotel-delete-dialog.component.html',
})
export class HotelDeleteDialogComponent {
  hotel?: IHotel;

  constructor(protected hotelService: HotelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hotelListModification');
      this.activeModal.close();
    });
  }
}
