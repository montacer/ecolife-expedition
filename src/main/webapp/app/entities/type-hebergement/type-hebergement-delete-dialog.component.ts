import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';
import { TypeHebergementService } from './type-hebergement.service';

@Component({
  templateUrl: './type-hebergement-delete-dialog.component.html',
})
export class TypeHebergementDeleteDialogComponent {
  typeHebergement?: ITypeHebergement;

  constructor(
    protected typeHebergementService: TypeHebergementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeHebergementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeHebergementListModification');
      this.activeModal.close();
    });
  }
}
