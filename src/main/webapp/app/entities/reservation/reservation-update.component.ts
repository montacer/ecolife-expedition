import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReservation, Reservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { IReservationTour } from 'app/shared/model/reservation-tour.model';
import { ReservationTourService } from 'app/entities/reservation-tour/reservation-tour.service';
import { IReservationHebergement } from 'app/shared/model/reservation-hebergement.model';
import { ReservationHebergementService } from 'app/entities/reservation-hebergement/reservation-hebergement.service';
import { IReservationTransfert } from 'app/shared/model/reservation-transfert.model';
import { ReservationTransfertService } from 'app/entities/reservation-transfert/reservation-transfert.service';
import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from 'app/entities/donneur-ordre/donneur-ordre.service';

type SelectableEntity = IReservationTour | IReservationHebergement | IReservationTransfert | IDonneurOrdre;

@Component({
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html',
})
export class ReservationUpdateComponent implements OnInit {
  isSaving = false;
  reservationtours: IReservationTour[] = [];
  reservationhebergements: IReservationHebergement[] = [];
  reservationtransferts: IReservationTransfert[] = [];
  donneurordres: IDonneurOrdre[] = [];
  dateDebutDp: any;
  dateFinDp: any;

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    nombreAdulte: [],
    nombreJeune: [],
    nombreEnfant: [],
    montantTotalTTC: [],
    libDevise: [],
    reservationTour: [],
    reservationHebergement: [],
    reservationTransfert: [],
    donneurOrdre: [],
  });

  constructor(
    protected reservationService: ReservationService,
    protected reservationTourService: ReservationTourService,
    protected reservationHebergementService: ReservationHebergementService,
    protected reservationTransfertService: ReservationTransfertService,
    protected donneurOrdreService: DonneurOrdreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservation }) => {
      this.updateForm(reservation);

      this.reservationTourService
        .query({ filter: 'reservation-is-null' })
        .pipe(
          map((res: HttpResponse<IReservationTour[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservationTour[]) => {
          if (!reservation.reservationTour || !reservation.reservationTour.id) {
            this.reservationtours = resBody;
          } else {
            this.reservationTourService
              .find(reservation.reservationTour.id)
              .pipe(
                map((subRes: HttpResponse<IReservationTour>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservationTour[]) => (this.reservationtours = concatRes));
          }
        });

      this.reservationHebergementService
        .query({ filter: 'reservation-is-null' })
        .pipe(
          map((res: HttpResponse<IReservationHebergement[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservationHebergement[]) => {
          if (!reservation.reservationHebergement || !reservation.reservationHebergement.id) {
            this.reservationhebergements = resBody;
          } else {
            this.reservationHebergementService
              .find(reservation.reservationHebergement.id)
              .pipe(
                map((subRes: HttpResponse<IReservationHebergement>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservationHebergement[]) => (this.reservationhebergements = concatRes));
          }
        });

      this.reservationTransfertService
        .query({ filter: 'reservation-is-null' })
        .pipe(
          map((res: HttpResponse<IReservationTransfert[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservationTransfert[]) => {
          if (!reservation.reservationTransfert || !reservation.reservationTransfert.id) {
            this.reservationtransferts = resBody;
          } else {
            this.reservationTransfertService
              .find(reservation.reservationTransfert.id)
              .pipe(
                map((subRes: HttpResponse<IReservationTransfert>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservationTransfert[]) => (this.reservationtransferts = concatRes));
          }
        });

      this.donneurOrdreService.query().subscribe((res: HttpResponse<IDonneurOrdre[]>) => (this.donneurordres = res.body || []));
    });
  }

  updateForm(reservation: IReservation): void {
    this.editForm.patchValue({
      id: reservation.id,
      dateDebut: reservation.dateDebut,
      dateFin: reservation.dateFin,
      nombreAdulte: reservation.nombreAdulte,
      nombreJeune: reservation.nombreJeune,
      nombreEnfant: reservation.nombreEnfant,
      montantTotalTTC: reservation.montantTotalTTC,
      libDevise: reservation.libDevise,
      reservationTour: reservation.reservationTour,
      reservationHebergement: reservation.reservationHebergement,
      reservationTransfert: reservation.reservationTransfert,
      donneurOrdre: reservation.donneurOrdre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservation = this.createFromForm();
    if (reservation.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationService.update(reservation));
    } else {
      this.subscribeToSaveResponse(this.reservationService.create(reservation));
    }
  }

  private createFromForm(): IReservation {
    return {
      ...new Reservation(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      nombreAdulte: this.editForm.get(['nombreAdulte'])!.value,
      nombreJeune: this.editForm.get(['nombreJeune'])!.value,
      nombreEnfant: this.editForm.get(['nombreEnfant'])!.value,
      montantTotalTTC: this.editForm.get(['montantTotalTTC'])!.value,
      libDevise: this.editForm.get(['libDevise'])!.value,
      reservationTour: this.editForm.get(['reservationTour'])!.value,
      reservationHebergement: this.editForm.get(['reservationHebergement'])!.value,
      reservationTransfert: this.editForm.get(['reservationTransfert'])!.value,
      donneurOrdre: this.editForm.get(['donneurOrdre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>): void {
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
