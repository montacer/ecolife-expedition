import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeRegion } from 'app/shared/model/type-region.model';
import { TypeRegionService } from './type-region.service';

@Component({
  templateUrl: './type-region-delete-dialog.component.html',
})
export class TypeRegionDeleteDialogComponent {
  typeRegion?: ITypeRegion;

  constructor(
    protected typeRegionService: TypeRegionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeRegionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeRegionListModification');
      this.activeModal.close();
    });
  }
}
