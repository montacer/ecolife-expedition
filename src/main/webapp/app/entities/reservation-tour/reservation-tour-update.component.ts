import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReservationTour, ReservationTour } from 'app/shared/model/reservation-tour.model';
import { ReservationTourService } from './reservation-tour.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';

@Component({
  selector: 'jhi-reservation-tour-update',
  templateUrl: './reservation-tour-update.component.html',
})
export class ReservationTourUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];

  editForm = this.fb.group({
    id: [],
    montantTotalTTC: [],
    reservation: [],
  });

  constructor(
    protected reservationTourService: ReservationTourService,
    protected reservationService: ReservationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationTour }) => {
      this.updateForm(reservationTour);

      this.reservationService
        .query({ filter: 'reservationtour-is-null' })
        .pipe(
          map((res: HttpResponse<IReservation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservation[]) => {
          if (!reservationTour.reservation || !reservationTour.reservation.id) {
            this.reservations = resBody;
          } else {
            this.reservationService
              .find(reservationTour.reservation.id)
              .pipe(
                map((subRes: HttpResponse<IReservation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservation[]) => (this.reservations = concatRes));
          }
        });
    });
  }

  updateForm(reservationTour: IReservationTour): void {
    this.editForm.patchValue({
      id: reservationTour.id,
      montantTotalTTC: reservationTour.montantTotalTTC,
      reservation: reservationTour.reservation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservationTour = this.createFromForm();
    if (reservationTour.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationTourService.update(reservationTour));
    } else {
      this.subscribeToSaveResponse(this.reservationTourService.create(reservationTour));
    }
  }

  private createFromForm(): IReservationTour {
    return {
      ...new ReservationTour(),
      id: this.editForm.get(['id'])!.value,
      montantTotalTTC: this.editForm.get(['montantTotalTTC'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservationTour>>): void {
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

  trackById(index: number, item: IReservation): any {
    return item.id;
  }
}
