import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from './type-transfert.service';

@Component({
  templateUrl: './type-transfert-delete-dialog.component.html',
})
export class TypeTransfertDeleteDialogComponent {
  typeTransfert?: ITypeTransfert;

  constructor(
    protected typeTransfertService: TypeTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeTransfertListModification');
      this.activeModal.close();
    });
  }
}
