import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisTransfert } from 'app/shared/model/avis-transfert.model';
import { AvisTransfertService } from './avis-transfert.service';
import { AvisTransfertDeleteDialogComponent } from './avis-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-avis-transfert',
  templateUrl: './avis-transfert.component.html',
})
export class AvisTransfertComponent implements OnInit, OnDestroy {
  avisTransferts?: IAvisTransfert[];
  eventSubscriber?: Subscription;

  constructor(
    protected avisTransfertService: AvisTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.avisTransfertService.query().subscribe((res: HttpResponse<IAvisTransfert[]>) => (this.avisTransferts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvisTransferts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvisTransfert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvisTransferts(): void {
    this.eventSubscriber = this.eventManager.subscribe('avisTransfertListModification', () => this.loadAll());
  }

  delete(avisTransfert: IAvisTransfert): void {
    const modalRef = this.modalService.open(AvisTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avisTransfert = avisTransfert;
  }
}
