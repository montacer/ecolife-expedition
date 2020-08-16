import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from './donneur-ordre.service';
import { DonneurOrdreDeleteDialogComponent } from './donneur-ordre-delete-dialog.component';

@Component({
  selector: 'jhi-donneur-ordre',
  templateUrl: './donneur-ordre.component.html',
})
export class DonneurOrdreComponent implements OnInit, OnDestroy {
  donneurOrdres?: IDonneurOrdre[];
  eventSubscriber?: Subscription;

  constructor(
    protected donneurOrdreService: DonneurOrdreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.donneurOrdreService.query().subscribe((res: HttpResponse<IDonneurOrdre[]>) => (this.donneurOrdres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDonneurOrdres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDonneurOrdre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDonneurOrdres(): void {
    this.eventSubscriber = this.eventManager.subscribe('donneurOrdreListModification', () => this.loadAll());
  }

  delete(donneurOrdre: IDonneurOrdre): void {
    const modalRef = this.modalService.open(DonneurOrdreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.donneurOrdre = donneurOrdre;
  }
}
