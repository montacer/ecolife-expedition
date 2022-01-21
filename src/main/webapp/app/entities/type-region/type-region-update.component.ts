import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeRegion, TypeRegion } from 'app/shared/model/type-region.model';
import { TypeRegionService } from './type-region.service';

@Component({
  selector: 'jhi-type-region-update',
  templateUrl: './type-region-update.component.html',
})
export class TypeRegionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTypeRegion: [],
  });

  constructor(protected typeRegionService: TypeRegionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRegion }) => {
      this.updateForm(typeRegion);
    });
  }

  updateForm(typeRegion: ITypeRegion): void {
    this.editForm.patchValue({
      id: typeRegion.id,
      libTypeRegion: typeRegion.libTypeRegion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeRegion = this.createFromForm();
    if (typeRegion.id !== undefined) {
      this.subscribeToSaveResponse(this.typeRegionService.update(typeRegion));
    } else {
      this.subscribeToSaveResponse(this.typeRegionService.create(typeRegion));
    }
  }

  private createFromForm(): ITypeRegion {
    return {
      ...new TypeRegion(),
      id: this.editForm.get(['id'])!.value,
      libTypeRegion: this.editForm.get(['libTypeRegion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeRegion>>): void {
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
