import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeRegion } from 'app/shared/model/type-region.model';
import { TypeRegionService } from './type-region.service';
import { TypeRegionDeleteDialogComponent } from './type-region-delete-dialog.component';

@Component({
  selector: 'jhi-type-region',
  templateUrl: './type-region.component.html',
})
export class TypeRegionComponent implements OnInit, OnDestroy {
  typeRegions?: ITypeRegion[];
  eventSubscriber?: Subscription;

  constructor(protected typeRegionService: TypeRegionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.typeRegionService.query().subscribe((res: HttpResponse<ITypeRegion[]>) => (this.typeRegions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeRegions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeRegion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeRegions(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeRegionListModification', () => this.loadAll());
  }

  delete(typeRegion: ITypeRegion): void {
    const modalRef = this.modalService.open(TypeRegionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeRegion = typeRegion;
  }
}
