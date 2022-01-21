import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifHebergment, TarifHebergment } from 'app/shared/model/tarif-hebergment.model';
import { TarifHebergmentService } from './tarif-hebergment.service';

@Component({
  selector: 'jhi-tarif-hebergment-update',
  templateUrl: './tarif-hebergment-update.component.html',
})
export class TarifHebergmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTarifHergement: [],
  });

  constructor(
    protected tarifHebergmentService: TarifHebergmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifHebergment }) => {
      this.updateForm(tarifHebergment);
    });
  }

  updateForm(tarifHebergment: ITarifHebergment): void {
    this.editForm.patchValue({
      id: tarifHebergment.id,
      libTarifHergement: tarifHebergment.libTarifHergement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifHebergment = this.createFromForm();
    if (tarifHebergment.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifHebergmentService.update(tarifHebergment));
    } else {
      this.subscribeToSaveResponse(this.tarifHebergmentService.create(tarifHebergment));
    }
  }

  private createFromForm(): ITarifHebergment {
    return {
      ...new TarifHebergment(),
      id: this.editForm.get(['id'])!.value,
      libTarifHergement: this.editForm.get(['libTarifHergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifHebergment>>): void {
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
}
