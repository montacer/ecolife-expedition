import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReservationTour } from 'app/shared/model/reservation-tour.model';
import { ReservationTourService } from './reservation-tour.service';
import { ReservationTourDeleteDialogComponent } from './reservation-tour-delete-dialog.component';

@Component({
  selector: 'jhi-reservation-tour',
  templateUrl: './reservation-tour.component.html',
})
export class ReservationTourComponent implements OnInit, OnDestroy {
  reservationTours?: IReservationTour[];
  eventSubscriber?: Subscription;

  constructor(
    protected reservationTourService: ReservationTourService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.reservationTourService.query().subscribe((res: HttpResponse<IReservationTour[]>) => (this.reservationTours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReservationTours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReservationTour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReservationTours(): void {
    this.eventSubscriber = this.eventManager.subscribe('reservationTourListModification', () => this.loadAll());
  }

  delete(reservationTour: IReservationTour): void {
    const modalRef = this.modalService.open(ReservationTourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reservationTour = reservationTour;
  }
}
