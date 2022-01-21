import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { ReservationHebergementService } from './reservation-hebergement.service';
import { ReservationHebergementDeleteDialogComponent } from './reservation-hebergement-delete-dialog.component';

@Component({
  selector: 'jhi-reservation-hebergement',
  templateUrl: './reservation-hebergement.component.html',
})
export class ReservationHebergementComponent implements OnInit, OnDestroy {
  reservationHebergements?: IReservationHebergement[];
  eventSubscriber?: Subscription;

  constructor(
    protected reservationHebergementService: ReservationHebergementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.reservationHebergementService
      .query()
      .subscribe((res: HttpResponse<IReservationHebergement[]>) => (this.reservationHebergements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReservationHebergements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReservationHebergement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReservationHebergements(): void {
    this.eventSubscriber = this.eventManager.subscribe('reservationHebergementListModification', () => this.loadAll());
  }

  delete(reservationHebergement: IReservationHebergement): void {
    const modalRef = this.modalService.open(ReservationHebergementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reservationHebergement = reservationHebergement;
  }
}
