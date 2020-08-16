import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgence } from 'app/shared/model/agence.model';
import { AgenceService } from './agence.service';
import { AgenceDeleteDialogComponent } from './agence-delete-dialog.component';

@Component({
  selector: 'jhi-agence',
  templateUrl: './agence.component.html',
})
export class AgenceComponent implements OnInit, OnDestroy {
  agences?: IAgence[];
  eventSubscriber?: Subscription;

  constructor(protected agenceService: AgenceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.agenceService.query().subscribe((res: HttpResponse<IAgence[]>) => (this.agences = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAgences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAgence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAgences(): void {
    this.eventSubscriber = this.eventManager.subscribe('agenceListModification', () => this.loadAll());
  }

  delete(agence: IAgence): void {
    const modalRef = this.modalService.open(AgenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agence = agence;
  }
}
