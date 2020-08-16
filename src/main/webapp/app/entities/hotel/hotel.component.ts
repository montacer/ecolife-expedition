import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from './hotel.service';
import { HotelDeleteDialogComponent } from './hotel-delete-dialog.component';

@Component({
  selector: 'jhi-hotel',
  templateUrl: './hotel.component.html',
})
export class HotelComponent implements OnInit, OnDestroy {
  hotels?: IHotel[];
  eventSubscriber?: Subscription;

  constructor(protected hotelService: HotelService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.hotelService.query().subscribe((res: HttpResponse<IHotel[]>) => (this.hotels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHotels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHotel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHotels(): void {
    this.eventSubscriber = this.eventManager.subscribe('hotelListModification', () => this.loadAll());
  }

  delete(hotel: IHotel): void {
    const modalRef = this.modalService.open(HotelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hotel = hotel;
  }
}
