import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { AvisHebergementService } from './avis-hebergement.service';
import { AvisHebergementDeleteDialogComponent } from './avis-hebergement-delete-dialog.component';

@Component({
  selector: 'jhi-avis-hebergement',
  templateUrl: './avis-hebergement.component.html',
})
export class AvisHebergementComponent implements OnInit, OnDestroy {
  avisHebergements?: IAvisHebergement[];
  eventSubscriber?: Subscription;

  constructor(
    protected avisHebergementService: AvisHebergementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.avisHebergementService.query().subscribe((res: HttpResponse<IAvisHebergement[]>) => (this.avisHebergements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvisHebergements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvisHebergement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvisHebergements(): void {
    this.eventSubscriber = this.eventManager.subscribe('avisHebergementListModification', () => this.loadAll());
  }

  delete(avisHebergement: IAvisHebergement): void {
    const modalRef = this.modalService.open(AvisHebergementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avisHebergement = avisHebergement;
  }
}
