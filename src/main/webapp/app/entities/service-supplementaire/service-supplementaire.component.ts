import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { ServiceSupplementaireService } from './service-supplementaire.service';
import { ServiceSupplementaireDeleteDialogComponent } from './service-supplementaire-delete-dialog.component';

@Component({
  selector: 'jhi-service-supplementaire',
  templateUrl: './service-supplementaire.component.html',
})
export class ServiceSupplementaireComponent implements OnInit, OnDestroy {
  serviceSupplementaires?: IServiceSupplementaire[];
  eventSubscriber?: Subscription;

  constructor(
    protected serviceSupplementaireService: ServiceSupplementaireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.serviceSupplementaireService
      .query()
      .subscribe((res: HttpResponse<IServiceSupplementaire[]>) => (this.serviceSupplementaires = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServiceSupplementaires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServiceSupplementaire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServiceSupplementaires(): void {
    this.eventSubscriber = this.eventManager.subscribe('serviceSupplementaireListModification', () => this.loadAll());
  }

  delete(serviceSupplementaire: IServiceSupplementaire): void {
    const modalRef = this.modalService.open(ServiceSupplementaireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.serviceSupplementaire = serviceSupplementaire;
  }
}
