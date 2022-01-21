import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReservationTransfert, ReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ReservationTransfertService } from './reservation-transfert.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';
import { ITransfert } from 'app/shared/model/transfert.model';
import { TransfertService } from 'app/entities/transfert/transfert.service';

type SelectableEntity = IReservation | ITransfert;

@Component({
  selector: 'jhi-reservation-transfert-update',
  templateUrl: './reservation-transfert-update.component.html',
})
export class ReservationTransfertUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];
  transferts: ITransfert[] = [];

  editForm = this.fb.group({
    id: [],
    montantTotalTTC: [],
    reservation: [],
    transfert: [],
  });

  constructor(
    protected reservationTransfertService: ReservationTransfertService,
    protected reservationService: ReservationService,
    protected transfertService: TransfertService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservationTransfert }) => {
      this.updateForm(reservationTransfert);

      this.reservationService
        .query({ filter: 'reservationtransfert-is-null' })
        .pipe(
          map((res: HttpResponse<IReservation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservation[]) => {
          if (!reservationTransfert.reservation || !reservationTransfert.reservation.id) {
            this.reservations = resBody;
          } else {
            this.reservationService
              .find(reservationTransfert.reservation.id)
              .pipe(
                map((subRes: HttpResponse<IReservation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservation[]) => (this.reservations = concatRes));
          }
        });

      this.transfertService.query().subscribe((res: HttpResponse<ITransfert[]>) => (this.transferts = res.body || []));
    });
  }

  updateForm(reservationTransfert: IReservationTransfert): void {
    this.editForm.patchValue({
      id: reservationTransfert.id,
      montantTotalTTC: reservationTransfert.montantTotalTTC,
      reservation: reservationTransfert.reservation,
      transfert: reservationTransfert.transfert,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservationTransfert = this.createFromForm();
    if (reservationTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationTransfertService.update(reservationTransfert));
    } else {
      this.subscribeToSaveResponse(this.reservationTransfertService.create(reservationTransfert));
    }
  }

  private createFromForm(): IReservationTransfert {
    return {
      ...new ReservationTransfert(),
      id: this.editForm.get(['id'])!.value,
      montantTotalTTC: this.editForm.get(['montantTotalTTC'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
      transfert: this.editForm.get(['transfert'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservationTransfert>>): void {
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
