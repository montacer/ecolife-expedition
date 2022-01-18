import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';
import { TarifServiceSupplementaireService } from './tarif-service-supplementaire.service';

@Component({
  templateUrl: './tarif-service-supplementaire-delete-dialog.component.html',
})
export class TarifServiceSupplementaireDeleteDialogComponent {
  tarifServiceSupplementaire?: ITarifServiceSupplementaire;

  constructor(
    protected tarifServiceSupplementaireService: TarifServiceSupplementaireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifServiceSupplementaireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifServiceSupplementaireListModification');
      this.activeModal.close();
    });
  }
}
