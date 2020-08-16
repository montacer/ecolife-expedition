import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { ChambreDeleteDialogComponent } from './chambre-delete-dialog.component';

@Component({
  selector: 'jhi-chambre',
  templateUrl: './chambre.component.html',
})
export class ChambreComponent implements OnInit, OnDestroy {
  chambres?: IChambre[];
  eventSubscriber?: Subscription;

  constructor(protected chambreService: ChambreService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.chambreService.query().subscribe((res: HttpResponse<IChambre[]>) => (this.chambres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChambres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChambre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChambres(): void {
    this.eventSubscriber = this.eventManager.subscribe('chambreListModification', () => this.loadAll());
  }

  delete(chambre: IChambre): void {
    const modalRef = this.modalService.open(ChambreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chambre = chambre;
  }
}
