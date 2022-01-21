import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisTour } from 'app/shared/model/avis-tour.model';
import { AvisTourService } from './avis-tour.service';
import { AvisTourDeleteDialogComponent } from './avis-tour-delete-dialog.component';

@Component({
  selector: 'jhi-avis-tour',
  templateUrl: './avis-tour.component.html',
})
export class AvisTourComponent implements OnInit, OnDestroy {
  avisTours?: IAvisTour[];
  eventSubscriber?: Subscription;

  constructor(protected avisTourService: AvisTourService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.avisTourService.query().subscribe((res: HttpResponse<IAvisTour[]>) => (this.avisTours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvisTours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvisTour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvisTours(): void {
    this.eventSubscriber = this.eventManager.subscribe('avisTourListModification', () => this.loadAll());
  }

  delete(avisTour: IAvisTour): void {
    const modalRef = this.modalService.open(AvisTourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avisTour = avisTour;
  }
}
