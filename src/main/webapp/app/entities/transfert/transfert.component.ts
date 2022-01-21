import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransfert } from 'app/shared/model/transfert.model';
import { TransfertService } from './transfert.service';
import { TransfertDeleteDialogComponent } from './transfert-delete-dialog.component';

@Component({
  selector: 'jhi-transfert',
  templateUrl: './transfert.component.html',
})
export class TransfertComponent implements OnInit, OnDestroy {
  transferts?: ITransfert[];
  eventSubscriber?: Subscription;

  constructor(protected transfertService: TransfertService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.transfertService.query().subscribe((res: HttpResponse<ITransfert[]>) => (this.transferts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransferts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransfert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransferts(): void {
    this.eventSubscriber = this.eventManager.subscribe('transfertListModification', () => this.loadAll());
  }

  delete(transfert: ITransfert): void {
    const modalRef = this.modalService.open(TransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transfert = transfert;
  }
}
