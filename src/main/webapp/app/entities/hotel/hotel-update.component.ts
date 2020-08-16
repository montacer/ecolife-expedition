import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHotel, Hotel } from 'app/shared/model/hotel.model';
import { HotelService } from './hotel.service';
import { IAgence } from 'app/shared/model/agence.model';
import { AgenceService } from 'app/entities/agence/agence.service';

@Component({
  selector: 'jhi-hotel-update',
  templateUrl: './hotel-update.component.html',
})
export class HotelUpdateComponent implements OnInit {
  isSaving = false;
  agences: IAgence[] = [];

  editForm = this.fb.group({
    id: [],
    libelleHotel: [],
    classeHotel: [],
    agence: [],
  });

  constructor(
    protected hotelService: HotelService,
    protected agenceService: AgenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotel }) => {
      this.updateForm(hotel);

      this.agenceService.query().subscribe((res: HttpResponse<IAgence[]>) => (this.agences = res.body || []));
    });
  }

  updateForm(hotel: IHotel): void {
    this.editForm.patchValue({
      id: hotel.id,
      libelleHotel: hotel.libelleHotel,
      classeHotel: hotel.classeHotel,
      agence: hotel.agence,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotel = this.createFromForm();
    if (hotel.id !== undefined) {
      this.subscribeToSaveResponse(this.hotelService.update(hotel));
    } else {
      this.subscribeToSaveResponse(this.hotelService.create(hotel));
    }
  }

  private createFromForm(): IHotel {
    return {
      ...new Hotel(),
      id: this.editForm.get(['id'])!.value,
      libelleHotel: this.editForm.get(['libelleHotel'])!.value,
      classeHotel: this.editForm.get(['classeHotel'])!.value,
      agence: this.editForm.get(['agence'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotel>>): void {
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

  trackById(index: number, item: IAgence): any {
    return item.id;
  }
}
