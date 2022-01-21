import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';
import { TarifTransfertService } from './tarif-transfert.service';

@Component({
  templateUrl: './tarif-transfert-delete-dialog.component.html',
})
export class TarifTransfertDeleteDialogComponent {
  tarifTransfert?: ITarifTransfert;

  constructor(
    protected tarifTransfertService: TarifTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifTransfertListModification');
      this.activeModal.close();
    });
  }
}
