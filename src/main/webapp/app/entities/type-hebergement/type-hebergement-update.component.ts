import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeHebergement, TypeHebergement } from 'app/shared/model/type-hebergement.model';
import { TypeHebergementService } from './type-hebergement.service';

@Component({
  selector: 'jhi-type-hebergement-update',
  templateUrl: './type-hebergement-update.component.html',
})
export class TypeHebergementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTypeHebergement: [],
  });

  constructor(
    protected typeHebergementService: TypeHebergementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeHebergement }) => {
      this.updateForm(typeHebergement);
    });
  }

  updateForm(typeHebergement: ITypeHebergement): void {
    this.editForm.patchValue({
      id: typeHebergement.id,
      libTypeHebergement: typeHebergement.libTypeHebergement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeHebergement = this.createFromForm();
    if (typeHebergement.id !== undefined) {
      this.subscribeToSaveResponse(this.typeHebergementService.update(typeHebergement));
    } else {
      this.subscribeToSaveResponse(this.typeHebergementService.create(typeHebergement));
    }
  }

  private createFromForm(): ITypeHebergement {
    return {
      ...new TypeHebergement(),
      id: this.editForm.get(['id'])!.value,
      libTypeHebergement: this.editForm.get(['libTypeHebergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeHebergement>>): void {
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
