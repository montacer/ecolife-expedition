import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';
import { HebergementDeleteDialogComponent } from './hebergement-delete-dialog.component';

@Component({
  selector: 'jhi-hebergement',
  templateUrl: './hebergement.component.html',
})
export class HebergementComponent implements OnInit, OnDestroy {
  hebergements?: IHebergement[];
  eventSubscriber?: Subscription;

  constructor(
    protected hebergementService: HebergementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.hebergementService.query().subscribe((res: HttpResponse<IHebergement[]>) => (this.hebergements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHebergements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHebergement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHebergements(): void {
    this.eventSubscriber = this.eventManager.subscribe('hebergementListModification', () => this.loadAll());
  }

  delete(hebergement: IHebergement): void {
    const modalRef = this.modalService.open(HebergementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hebergement = hebergement;
  }
}
