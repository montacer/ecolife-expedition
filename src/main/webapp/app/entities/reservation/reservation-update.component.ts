import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReservation, Reservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from 'app/entities/donneur-ordre/donneur-ordre.service';

@Component({
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html',
})
export class ReservationUpdateComponent implements OnInit {
  isSaving = false;
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
    montantTTC: [],
    libDevise: [],
    donneurOrdre: [],
  });

  constructor(
    protected reservationService: ReservationService,
    protected donneurOrdreService: DonneurOrdreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservation }) => {
      this.updateForm(reservation);

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
      montantTTC: reservation.montantTTC,
      libDevise: reservation.libDevise,
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
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      libDevise: this.editForm.get(['libDevise'])!.value,
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

  trackById(index: number, item: IDonneurOrdre): any {
    return item.id;
  }
}
