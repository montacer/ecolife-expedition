import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from './donneur-ordre.service';

@Component({
  templateUrl: './donneur-ordre-delete-dialog.component.html',
})
export class DonneurOrdreDeleteDialogComponent {
  donneurOrdre?: IDonneurOrdre;

  constructor(
    protected donneurOrdreService: DonneurOrdreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donneurOrdreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('donneurOrdreListModification');
      this.activeModal.close();
    });
  }
}
