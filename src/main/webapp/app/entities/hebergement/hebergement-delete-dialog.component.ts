import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';

@Component({
  templateUrl: './hebergement-delete-dialog.component.html',
})
export class HebergementDeleteDialogComponent {
  hebergement?: IHebergement;

  constructor(
    protected hebergementService: HebergementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hebergementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hebergementListModification');
      this.activeModal.close();
    });
  }
}
