import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifTour, TarifTour } from 'app/shared/model/tarif-tour.model';
import { TarifTourService } from './tarif-tour.service';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';

@Component({
  selector: 'jhi-tarif-tour-update',
  templateUrl: './tarif-tour-update.component.html',
})
export class TarifTourUpdateComponent implements OnInit {
  isSaving = false;
  typetarifs: ITypeTarif[] = [];

  editForm = this.fb.group({
    id: [],
    montantTTC: [],
    typeTarif: [],
  });

  constructor(
    protected tarifTourService: TarifTourService,
    protected typeTarifService: TypeTarifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTour }) => {
      this.updateForm(tarifTour);

      this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typetarifs = res.body || []));
    });
  }

  updateForm(tarifTour: ITarifTour): void {
    this.editForm.patchValue({
      id: tarifTour.id,
      montantTTC: tarifTour.montantTTC,
      typeTarif: tarifTour.typeTarif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifTour = this.createFromForm();
    if (tarifTour.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifTourService.update(tarifTour));
    } else {
      this.subscribeToSaveResponse(this.tarifTourService.create(tarifTour));
    }
  }

  private createFromForm(): ITarifTour {
    return {
      ...new TarifTour(),
      id: this.editForm.get(['id'])!.value,
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      typeTarif: this.editForm.get(['typeTarif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifTour>>): void {
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

  trackById(index: number, item: ITypeTarif): any {
    return item.id;
  }
}
