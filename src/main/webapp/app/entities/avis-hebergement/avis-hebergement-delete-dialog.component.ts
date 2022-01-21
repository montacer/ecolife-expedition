import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { AvisHebergementService } from './avis-hebergement.service';

@Component({
  templateUrl: './avis-hebergement-delete-dialog.component.html',
})
export class AvisHebergementDeleteDialogComponent {
  avisHebergement?: IAvisHebergement;

  constructor(
    protected avisHebergementService: AvisHebergementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisHebergementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisHebergementListModification');
      this.activeModal.close();
    });
  }
}
