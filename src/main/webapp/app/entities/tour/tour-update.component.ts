import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITour, Tour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from 'app/entities/type-circuit/type-circuit.service';

type SelectableEntity = IReservation | IRegion | ITypeCircuit;

@Component({
  selector: 'jhi-tour-update',
  templateUrl: './tour-update.component.html',
})
export class TourUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];
  regions: IRegion[] = [];
  typecircuits: ITypeCircuit[] = [];

  editForm = this.fb.group({
    id: [],
    libTitre: [],
    imageUrl: [],
    videoUrl: [],
    conseil: [],
    prixTTC: [],
    reservation: [],
    region: [],
    typeCircuit: [],
  });

  constructor(
    protected tourService: TourService,
    protected reservationService: ReservationService,
    protected regionService: RegionService,
    protected typeCircuitService: TypeCircuitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tour }) => {
      this.updateForm(tour);

      this.reservationService
        .query({ filter: 'tour-is-null' })
        .pipe(
          map((res: HttpResponse<IReservation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IReservation[]) => {
          if (!tour.reservation || !tour.reservation.id) {
            this.reservations = resBody;
          } else {
            this.reservationService
              .find(tour.reservation.id)
              .pipe(
                map((subRes: HttpResponse<IReservation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IReservation[]) => (this.reservations = concatRes));
          }
        });

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.typeCircuitService.query().subscribe((res: HttpResponse<ITypeCircuit[]>) => (this.typecircuits = res.body || []));
    });
  }

  updateForm(tour: ITour): void {
    this.editForm.patchValue({
      id: tour.id,
      libTitre: tour.libTitre,
      imageUrl: tour.imageUrl,
      videoUrl: tour.videoUrl,
      conseil: tour.conseil,
      prixTTC: tour.prixTTC,
      reservation: tour.reservation,
      region: tour.region,
      typeCircuit: tour.typeCircuit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tour = this.createFromForm();
    if (tour.id !== undefined) {
      this.subscribeToSaveResponse(this.tourService.update(tour));
    } else {
      this.subscribeToSaveResponse(this.tourService.create(tour));
    }
  }

  private createFromForm(): ITour {
    return {
      ...new Tour(),
      id: this.editForm.get(['id'])!.value,
      libTitre: this.editForm.get(['libTitre'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      conseil: this.editForm.get(['conseil'])!.value,
      prixTTC: this.editForm.get(['prixTTC'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
      region: this.editForm.get(['region'])!.value,
      typeCircuit: this.editForm.get(['typeCircuit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITour>>): void {
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
