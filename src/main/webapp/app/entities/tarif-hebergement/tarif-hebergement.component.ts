import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifHebergement } from 'app/shared/model/tarif-hebergement.model';
import { TarifHebergementService } from './tarif-hebergement.service';
import { TarifHebergementDeleteDialogComponent } from './tarif-hebergement-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-hebergement',
  templateUrl: './tarif-hebergement.component.html',
})
export class TarifHebergementComponent implements OnInit, OnDestroy {
  tarifHebergements?: ITarifHebergement[];
  eventSubscriber?: Subscription;

  constructor(
    protected tarifHebergementService: TarifHebergementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tarifHebergementService.query().subscribe((res: HttpResponse<ITarifHebergement[]>) => (this.tarifHebergements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifHebergements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifHebergement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifHebergements(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifHebergementListModification', () => this.loadAll());
  }

  delete(tarifHebergement: ITarifHebergement): void {
    const modalRef = this.modalService.open(TarifHebergementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifHebergement = tarifHebergement;
  }
}
