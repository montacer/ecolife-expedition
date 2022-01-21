import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { AvisTourMediaService } from './avis-tour-media.service';

@Component({
  templateUrl: './avis-tour-media-delete-dialog.component.html',
})
export class AvisTourMediaDeleteDialogComponent {
  avisTourMedia?: IAvisTourMedia;

  constructor(
    protected avisTourMediaService: AvisTourMediaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisTourMediaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisTourMediaListModification');
      this.activeModal.close();
    });
  }
}
