import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';
import { TourDeleteDialogComponent } from './tour-delete-dialog.component';

@Component({
  selector: 'jhi-tour',
  templateUrl: './tour.component.html',
})
export class TourComponent implements OnInit, OnDestroy {
  tours?: ITour[];
  eventSubscriber?: Subscription;

  constructor(
    protected tourService: TourService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tourService.query().subscribe((res: HttpResponse<ITour[]>) => (this.tours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTours(): void {
    this.eventSubscriber = this.eventManager.subscribe('tourListModification', () => this.loadAll());
  }

  delete(tour: ITour): void {
    const modalRef = this.modalService.open(TourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tour = tour;
  }
}
