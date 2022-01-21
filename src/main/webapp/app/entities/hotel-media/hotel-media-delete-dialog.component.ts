import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHotelMedia } from 'app/shared/model/hotel-media.model';
import { HotelMediaService } from './hotel-media.service';

@Component({
  templateUrl: './hotel-media-delete-dialog.component.html',
})
export class HotelMediaDeleteDialogComponent {
  hotelMedia?: IHotelMedia;

  constructor(
    protected hotelMediaService: HotelMediaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelMediaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hotelMediaListModification');
      this.activeModal.close();
    });
  }
}
