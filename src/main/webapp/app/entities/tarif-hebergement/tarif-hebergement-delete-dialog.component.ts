import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifHebergement } from 'app/shared/model/tarif-hebergement.model';
import { TarifHebergementService } from './tarif-hebergement.service';

@Component({
  templateUrl: './tarif-hebergement-delete-dialog.component.html',
})
export class TarifHebergementDeleteDialogComponent {
  tarifHebergement?: ITarifHebergement;

  constructor(
    protected tarifHebergementService: TarifHebergementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifHebergementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifHebergementListModification');
      this.activeModal.close();
    });
  }
}
