import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITourMedia } from 'app/shared/model/tour-media.model';
import { TourMediaService } from './tour-media.service';

@Component({
  templateUrl: './tour-media-delete-dialog.component.html',
})
export class TourMediaDeleteDialogComponent {
  tourMedia?: ITourMedia;

  constructor(protected tourMediaService: TourMediaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tourMediaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tourMediaListModification');
      this.activeModal.close();
    });
  }
}
