import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from './type-circuit.service';

@Component({
  templateUrl: './type-circuit-delete-dialog.component.html',
})
export class TypeCircuitDeleteDialogComponent {
  typeCircuit?: ITypeCircuit;

  constructor(
    protected typeCircuitService: TypeCircuitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeCircuitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeCircuitListModification');
      this.activeModal.close();
    });
  }
}
