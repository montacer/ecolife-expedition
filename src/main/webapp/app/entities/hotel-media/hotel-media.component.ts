import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelMedia } from 'app/shared/model/hotel-media.model';
import { HotelMediaService } from './hotel-media.service';
import { HotelMediaDeleteDialogComponent } from './hotel-media-delete-dialog.component';

@Component({
  selector: 'jhi-hotel-media',
  templateUrl: './hotel-media.component.html',
})
export class HotelMediaComponent implements OnInit, OnDestroy {
  hotelMedias?: IHotelMedia[];
  eventSubscriber?: Subscription;

  constructor(
    protected hotelMediaService: HotelMediaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.hotelMediaService.query().subscribe((res: HttpResponse<IHotelMedia[]>) => (this.hotelMedias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHotelMedias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHotelMedia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInHotelMedias(): void {
    this.eventSubscriber = this.eventManager.subscribe('hotelMediaListModification', () => this.loadAll());
  }

  delete(hotelMedia: IHotelMedia): void {
    const modalRef = this.modalService.open(HotelMediaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hotelMedia = hotelMedia;
  }
}
