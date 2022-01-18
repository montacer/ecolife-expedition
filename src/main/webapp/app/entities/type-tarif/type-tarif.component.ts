import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from './type-tarif.service';
import { TypeTarifDeleteDialogComponent } from './type-tarif-delete-dialog.component';

@Component({
  selector: 'jhi-type-tarif',
  templateUrl: './type-tarif.component.html',
})
export class TypeTarifComponent implements OnInit, OnDestroy {
  typeTarifs?: ITypeTarif[];
  eventSubscriber?: Subscription;

  constructor(protected typeTarifService: TypeTarifService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typeTarifs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeTarifs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeTarif): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeTarifs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeTarifListModification', () => this.loadAll());
  }

  delete(typeTarif: ITypeTarif): void {
    const modalRef = this.modalService.open(TypeTarifDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeTarif = typeTarif;
  }
}
