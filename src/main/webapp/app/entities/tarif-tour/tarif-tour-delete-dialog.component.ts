import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifTour } from 'app/shared/model/tarif-tour.model';
import { TarifTourService } from './tarif-tour.service';

@Component({
  templateUrl: './tarif-tour-delete-dialog.component.html',
})
export class TarifTourDeleteDialogComponent {
  tarifTour?: ITarifTour;

  constructor(protected tarifTourService: TarifTourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifTourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifTourListModification');
      this.activeModal.close();
    });
  }
}
