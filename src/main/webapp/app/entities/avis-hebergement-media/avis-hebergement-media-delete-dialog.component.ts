import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { AvisHebergementMediaService } from './avis-hebergement-media.service';

@Component({
  templateUrl: './avis-hebergement-media-delete-dialog.component.html',
})
export class AvisHebergementMediaDeleteDialogComponent {
  avisHebergementMedia?: IAvisHebergementMedia;

  constructor(
    protected avisHebergementMediaService: AvisHebergementMediaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisHebergementMediaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisHebergementMediaListModification');
      this.activeModal.close();
    });
  }
}
