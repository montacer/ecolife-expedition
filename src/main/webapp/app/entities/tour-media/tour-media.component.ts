import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITourMedia } from 'app/shared/model/tour-media.model';
import { TourMediaService } from './tour-media.service';
import { TourMediaDeleteDialogComponent } from './tour-media-delete-dialog.component';

@Component({
  selector: 'jhi-tour-media',
  templateUrl: './tour-media.component.html',
})
export class TourMediaComponent implements OnInit, OnDestroy {
  tourMedias?: ITourMedia[];
  eventSubscriber?: Subscription;

  constructor(
    protected tourMediaService: TourMediaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tourMediaService.query().subscribe((res: HttpResponse<ITourMedia[]>) => (this.tourMedias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTourMedias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITourMedia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTourMedias(): void {
    this.eventSubscriber = this.eventManager.subscribe('tourMediaListModification', () => this.loadAll());
  }

  delete(tourMedia: ITourMedia): void {
    const modalRef = this.modalService.open(TourMediaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tourMedia = tourMedia;
  }
}
