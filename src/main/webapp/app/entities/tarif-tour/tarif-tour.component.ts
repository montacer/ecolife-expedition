import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifTour } from 'app/shared/model/tarif-tour.model';
import { TarifTourService } from './tarif-tour.service';
import { TarifTourDeleteDialogComponent } from './tarif-tour-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-tour',
  templateUrl: './tarif-tour.component.html',
})
export class TarifTourComponent implements OnInit, OnDestroy {
  tarifTours?: ITarifTour[];
  eventSubscriber?: Subscription;

  constructor(protected tarifTourService: TarifTourService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tarifTourService.query().subscribe((res: HttpResponse<ITarifTour[]>) => (this.tarifTours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifTours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifTour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifTours(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifTourListModification', () => this.loadAll());
  }

  delete(tarifTour: ITarifTour): void {
    const modalRef = this.modalService.open(TarifTourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifTour = tarifTour;
  }
}
