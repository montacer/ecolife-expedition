import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { AvisTourMediaService } from './avis-tour-media.service';
import { AvisTourMediaDeleteDialogComponent } from './avis-tour-media-delete-dialog.component';

@Component({
  selector: 'jhi-avis-tour-media',
  templateUrl: './avis-tour-media.component.html',
})
export class AvisTourMediaComponent implements OnInit, OnDestroy {
  avisTourMedias?: IAvisTourMedia[];
  eventSubscriber?: Subscription;

  constructor(
    protected avisTourMediaService: AvisTourMediaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.avisTourMediaService.query().subscribe((res: HttpResponse<IAvisTourMedia[]>) => (this.avisTourMedias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvisTourMedias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvisTourMedia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAvisTourMedias(): void {
    this.eventSubscriber = this.eventManager.subscribe('avisTourMediaListModification', () => this.loadAll());
  }

  delete(avisTourMedia: IAvisTourMedia): void {
    const modalRef = this.modalService.open(AvisTourMediaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avisTourMedia = avisTourMedia;
  }
}
