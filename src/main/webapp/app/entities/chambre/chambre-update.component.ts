import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChambre, Chambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from 'app/entities/type-chambre/type-chambre.service';
import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from 'app/entities/hotel/hotel.service';

type SelectableEntity = IReservation | ITypeChambre | IHotel;

@Component({
  selector: 'jhi-chambre-update',
  templateUrl: './chambre-update.component.html',
})
export class ChambreUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];
  typechambres: ITypeChambre[] = [];
  hotels: IHotel[] = [];

  editForm = this.fb.group({
    id: [],
    prixTTC: [],
    reservation: [],
    typeChambre: [],
    hotel: [],
  });

  constructor(
    protected chambreService: ChambreService,
    protected reservationService: ReservationService,
    protected typeChambreService: TypeChambreService,
    protected hotelService: HotelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chambre }) => {
      this.updateForm(chambre);

      this.reservationService.query().subscribe((res: HttpResponse<IReservation[]>) => (this.reservations = res.body || []));

      this.typeChambreService.query().subscribe((res: HttpResponse<ITypeChambre[]>) => (this.typechambres = res.body || []));

      this.hotelService.query().subscribe((res: HttpResponse<IHotel[]>) => (this.hotels = res.body || []));
    });
  }

  updateForm(chambre: IChambre): void {
    this.editForm.patchValue({
      id: chambre.id,
      prixTTC: chambre.prixTTC,
      reservation: chambre.reservation,
      typeChambre: chambre.typeChambre,
      hotel: chambre.hotel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chambre = this.createFromForm();
    if (chambre.id !== undefined) {
      this.subscribeToSaveResponse(this.chambreService.update(chambre));
    } else {
      this.subscribeToSaveResponse(this.chambreService.create(chambre));
    }
  }

  private createFromForm(): IChambre {
    return {
      ...new Chambre(),
      id: this.editForm.get(['id'])!.value,
      prixTTC: this.editForm.get(['prixTTC'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
      typeChambre: this.editForm.get(['typeChambre'])!.value,
      hotel: this.editForm.get(['hotel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChambre>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
