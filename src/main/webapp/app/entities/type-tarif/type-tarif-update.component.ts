import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeTarif, TypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from './type-tarif.service';

@Component({
  selector: 'jhi-type-tarif-update',
  templateUrl: './type-tarif-update.component.html',
})
export class TypeTarifUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTypeTarif: [],
  });

  constructor(protected typeTarifService: TypeTarifService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeTarif }) => {
      this.updateForm(typeTarif);
    });
  }

  updateForm(typeTarif: ITypeTarif): void {
    this.editForm.patchValue({
      id: typeTarif.id,
      libTypeTarif: typeTarif.libTypeTarif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeTarif = this.createFromForm();
    if (typeTarif.id !== undefined) {
      this.subscribeToSaveResponse(this.typeTarifService.update(typeTarif));
    } else {
      this.subscribeToSaveResponse(this.typeTarifService.create(typeTarif));
    }
  }

  private createFromForm(): ITypeTarif {
    return {
      ...new TypeTarif(),
      id: this.editForm.get(['id'])!.value,
      libTypeTarif: this.editForm.get(['libTypeTarif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTarif>>): void {
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
