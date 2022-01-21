import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifHebergment } from 'app/shared/model/tarif-hebergment.model';
import { TarifHebergmentService } from './tarif-hebergment.service';
import { TarifHebergmentDeleteDialogComponent } from './tarif-hebergment-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-hebergment',
  templateUrl: './tarif-hebergment.component.html',
})
export class TarifHebergmentComponent implements OnInit, OnDestroy {
  tarifHebergments?: ITarifHebergment[];
  eventSubscriber?: Subscription;

  constructor(
    protected tarifHebergmentService: TarifHebergmentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tarifHebergmentService.query().subscribe((res: HttpResponse<ITarifHebergment[]>) => (this.tarifHebergments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifHebergments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifHebergment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifHebergments(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifHebergmentListModification', () => this.loadAll());
  }

  delete(tarifHebergment: ITarifHebergment): void {
    const modalRef = this.modalService.open(TarifHebergmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifHebergment = tarifHebergment;
  }
}
