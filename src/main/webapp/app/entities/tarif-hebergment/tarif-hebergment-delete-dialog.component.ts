import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifHebergment } from 'app/shared/model/tarif-hebergment.model';
import { TarifHebergmentService } from './tarif-hebergment.service';

@Component({
  templateUrl: './tarif-hebergment-delete-dialog.component.html',
})
export class TarifHebergmentDeleteDialogComponent {
  tarifHebergment?: ITarifHebergment;

  constructor(
    protected tarifHebergmentService: TarifHebergmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifHebergmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifHebergmentListModification');
      this.activeModal.close();
    });
  }
}
