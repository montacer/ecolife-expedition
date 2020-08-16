import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { ServiceSupplementaireService } from './service-supplementaire.service';

@Component({
  templateUrl: './service-supplementaire-delete-dialog.component.html',
})
export class ServiceSupplementaireDeleteDialogComponent {
  serviceSupplementaire?: IServiceSupplementaire;

  constructor(
    protected serviceSupplementaireService: ServiceSupplementaireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceSupplementaireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceSupplementaireListModification');
      this.activeModal.close();
    });
  }
}
