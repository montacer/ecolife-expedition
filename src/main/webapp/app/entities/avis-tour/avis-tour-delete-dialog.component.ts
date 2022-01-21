import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisTour } from 'app/shared/model/avis-tour.model';
import { AvisTourService } from './avis-tour.service';

@Component({
  templateUrl: './avis-tour-delete-dialog.component.html',
})
export class AvisTourDeleteDialogComponent {
  avisTour?: IAvisTour;

  constructor(protected avisTourService: AvisTourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisTourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisTourListModification');
      this.activeModal.close();
    });
  }
}
