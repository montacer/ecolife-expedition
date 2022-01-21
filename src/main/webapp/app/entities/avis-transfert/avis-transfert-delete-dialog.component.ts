import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisTransfert } from 'app/shared/model/avis-transfert.model';
import { AvisTransfertService } from './avis-transfert.service';

@Component({
  templateUrl: './avis-transfert-delete-dialog.component.html',
})
export class AvisTransfertDeleteDialogComponent {
  avisTransfert?: IAvisTransfert;

  constructor(
    protected avisTransfertService: AvisTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisTransfertListModification');
      this.activeModal.close();
    });
  }
}
