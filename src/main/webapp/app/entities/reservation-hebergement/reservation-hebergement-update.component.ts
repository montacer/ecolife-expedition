import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReservationHebergement, ReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { ReservationHebergementService } from './reservation-hebergement.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';
import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from 'app/entities/hebergement/hebergement.service';

type SelectableEntity = IReservation | IHebergement;

@Component({
  selector: 'jhi-reservation-hebergement-update',
  templateUrl: './reservation-hebergement-update.component.html',
})
export class ReservationHebergementUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];
  hebergements: IHebergement[] = [];

  editForm = this.fb.group({
    id: [],
    montantTotalTTC: [],
    reservation: [],
    hebergement: [],
  });

  constructor(
    protected reservationHebergementService: ReservationHebergementService,
    protected reservationService: ReservationService,
    protected hebergementService: HebergementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationHebergement }) => {
      this.updateForm(reservationHebergement);

      this.reservationService
        .query({ filter: 'reservationhebergement-is-null' })
        .pipe(
          map((res: HttpResponse<IReservation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservation[]) => {
          if (!reservationHebergement.reservation || !reservationHebergement.reservation.id) {
            this.reservations = resBody;
          } else {
            this.reservationService
              .find(reservationHebergement.reservation.id)
              .pipe(
                map((subRes: HttpResponse<IReservation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservation[]) => (this.reservations = concatRes));
          }
        });

      this.hebergementService.query().subscribe((res: HttpResponse<IHebergement[]>) => (this.hebergements = res.body || []));
    });
  }

  updateForm(reservationHebergement: IReservationHebergement): void {
    this.editForm.patchValue({
      id: reservationHebergement.id,
      montantTotalTTC: reservationHebergement.montantTotalTTC,
      reservation: reservationHebergement.reservation,
      hebergement: reservationHebergement.hebergement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservationHebergement = this.createFromForm();
    if (reservationHebergement.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationHebergementService.update(reservationHebergement));
    } else {
      this.subscribeToSaveResponse(this.reservationHebergementService.create(reservationHebergement));
    }
  }

  private createFromForm(): IReservationHebergement {
    return {
      ...new ReservationHebergement(),
      id: this.editForm.get(['id'])!.value,
      montantTotalTTC: this.editForm.get(['montantTotalTTC'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
      hebergement: this.editForm.get(['hebergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservationHebergement>>): void {
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
