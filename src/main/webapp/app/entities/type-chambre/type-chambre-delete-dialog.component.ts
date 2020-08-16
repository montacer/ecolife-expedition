import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';

@Component({
  templateUrl: './type-chambre-delete-dialog.component.html',
})
export class TypeChambreDeleteDialogComponent {
  typeChambre?: ITypeChambre;

  constructor(
    protected typeChambreService: TypeChambreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeChambreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeChambreListModification');
      this.activeModal.close();
    });
  }
}
