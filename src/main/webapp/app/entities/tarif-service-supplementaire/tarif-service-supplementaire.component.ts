import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';
import { TarifServiceSupplementaireService } from './tarif-service-supplementaire.service';
import { TarifServiceSupplementaireDeleteDialogComponent } from './tarif-service-supplementaire-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-service-supplementaire',
  templateUrl: './tarif-service-supplementaire.component.html',
})
export class TarifServiceSupplementaireComponent implements OnInit, OnDestroy {
  tarifServiceSupplementaires?: ITarifServiceSupplementaire[];
  eventSubscriber?: Subscription;

  constructor(
    protected tarifServiceSupplementaireService: TarifServiceSupplementaireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tarifServiceSupplementaireService
      .query()
      .subscribe((res: HttpResponse<ITarifServiceSupplementaire[]>) => (this.tarifServiceSupplementaires = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifServiceSupplementaires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifServiceSupplementaire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifServiceSupplementaires(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifServiceSupplementaireListModification', () => this.loadAll());
  }

  delete(tarifServiceSupplementaire: ITarifServiceSupplementaire): void {
    const modalRef = this.modalService.open(TarifServiceSupplementaireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifServiceSupplementaire = tarifServiceSupplementaire;
  }
}
