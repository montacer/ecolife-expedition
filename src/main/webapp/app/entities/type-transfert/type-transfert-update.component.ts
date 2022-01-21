import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeTransfert, TypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from './type-transfert.service';

@Component({
  selector: 'jhi-type-transfert-update',
  templateUrl: './type-transfert-update.component.html',
})
export class TypeTransfertUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTypeTransfert: [],
  });

  constructor(protected typeTransfertService: TypeTransfertService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeTransfert }) => {
      this.updateForm(typeTransfert);
    });
  }

  updateForm(typeTransfert: ITypeTransfert): void {
    this.editForm.patchValue({
      id: typeTransfert.id,
      libTypeTransfert: typeTransfert.libTypeTransfert,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeTransfert = this.createFromForm();
    if (typeTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.typeTransfertService.update(typeTransfert));
    } else {
      this.subscribeToSaveResponse(this.typeTransfertService.create(typeTransfert));
    }
  }

  private createFromForm(): ITypeTransfert {
    return {
      ...new TypeTransfert(),
      id: this.editForm.get(['id'])!.value,
      libTypeTransfert: this.editForm.get(['libTypeTransfert'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTransfert>>): void {
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
