import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeCircuit, TypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from './type-circuit.service';

@Component({
  selector: 'jhi-type-circuit-update',
  templateUrl: './type-circuit-update.component.html',
})
export class TypeCircuitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libTypeCircuit: [],
  });

  constructor(protected typeCircuitService: TypeCircuitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCircuit }) => {
      this.updateForm(typeCircuit);
    });
  }

  updateForm(typeCircuit: ITypeCircuit): void {
    this.editForm.patchValue({
      id: typeCircuit.id,
      libTypeCircuit: typeCircuit.libTypeCircuit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeCircuit = this.createFromForm();
    if (typeCircuit.id !== undefined) {
      this.subscribeToSaveResponse(this.typeCircuitService.update(typeCircuit));
    } else {
      this.subscribeToSaveResponse(this.typeCircuitService.create(typeCircuit));
    }
  }

  private createFromForm(): ITypeCircuit {
    return {
      ...new TypeCircuit(),
      id: this.editForm.get(['id'])!.value,
      libTypeCircuit: this.editForm.get(['libTypeCircuit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCircuit>>): void {
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
