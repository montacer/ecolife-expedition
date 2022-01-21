import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { AvisHebergementMediaService } from './avis-hebergement-media.service';
import { AvisHebergementMediaDeleteDialogComponent } from './avis-hebergement-media-delete-dialog.component';

@Component({
  selector: 'jhi-avis-hebergement-media',
  templateUrl: './avis-hebergement-media.component.html',
})
export class AvisHebergementMediaComponent implements OnInit, OnDestroy {
  avisHebergementMedias?: IAvisHebergementMedia[];
  eventSubscriber?: Subscription;

  constructor(
    protected avisHebergementMediaService: AvisHebergementMediaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.avisHebergementMediaService
      .query()
      .subscribe((res: HttpResponse<IAvisHebergementMedia[]>) => (this.avisHebergementMedias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvisHebergementMedias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvisHebergementMedia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAvisHebergementMedias(): void {
    this.eventSubscriber = this.eventManager.subscribe('avisHebergementMediaListModification', () => this.loadAll());
  }

  delete(avisHebergementMedia: IAvisHebergementMedia): void {
    const modalRef = this.modalService.open(AvisHebergementMediaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avisHebergementMedia = avisHebergementMedia;
  }
}
