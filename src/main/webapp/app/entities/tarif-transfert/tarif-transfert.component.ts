import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';
import { TarifTransfertService } from './tarif-transfert.service';
import { TarifTransfertDeleteDialogComponent } from './tarif-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-transfert',
  templateUrl: './tarif-transfert.component.html',
})
export class TarifTransfertComponent implements OnInit, OnDestroy {
  tarifTransferts?: ITarifTransfert[];
  eventSubscriber?: Subscription;

  constructor(
    protected tarifTransfertService: TarifTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tarifTransfertService.query().subscribe((res: HttpResponse<ITarifTransfert[]>) => (this.tarifTransferts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifTransferts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifTransfert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifTransferts(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifTransfertListModification', () => this.loadAll());
  }

  delete(tarifTransfert: ITarifTransfert): void {
    const modalRef = this.modalService.open(TarifTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifTransfert = tarifTransfert;
  }
}
