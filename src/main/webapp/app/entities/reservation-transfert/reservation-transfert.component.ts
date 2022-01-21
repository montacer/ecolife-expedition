import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ReservationTransfertService } from './reservation-transfert.service';
import { ReservationTransfertDeleteDialogComponent } from './reservation-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-reservation-transfert',
  templateUrl: './reservation-transfert.component.html',
})
export class ReservationTransfertComponent implements OnInit, OnDestroy {
  reservationTransferts?: IReservationTransfert[];
  eventSubscriber?: Subscription;

  constructor(
    protected reservationTransfertService: ReservationTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.reservationTransfertService
      .query()
      .subscribe((res: HttpResponse<IReservationTransfert[]>) => (this.reservationTransferts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReservationTransferts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReservationTransfert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReservationTransferts(): void {
    this.eventSubscriber = this.eventManager.subscribe('reservationTransfertListModification', () => this.loadAll());
  }

  delete(reservationTransfert: IReservationTransfert): void {
    const modalRef = this.modalService.open(ReservationTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reservationTransfert = reservationTransfert;
  }
}
