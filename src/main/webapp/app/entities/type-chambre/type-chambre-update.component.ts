import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeChambre, TypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';

@Component({
  selector: 'jhi-type-chambre-update',
  templateUrl: './type-chambre-update.component.html',
})
export class TypeChambreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleTypeChambre: [],
  });

  constructor(protected typeChambreService: TypeChambreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeChambre }) => {
      this.updateForm(typeChambre);
    });
  }

  updateForm(typeChambre: ITypeChambre): void {
    this.editForm.patchValue({
      id: typeChambre.id,
      libelleTypeChambre: typeChambre.libelleTypeChambre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeChambre = this.createFromForm();
    if (typeChambre.id !== undefined) {
      this.subscribeToSaveResponse(this.typeChambreService.update(typeChambre));
    } else {
      this.subscribeToSaveResponse(this.typeChambreService.create(typeChambre));
    }
  }

  private createFromForm(): ITypeChambre {
    return {
      ...new TypeChambre(),
      id: this.editForm.get(['id'])!.value,
      libelleTypeChambre: this.editForm.get(['libelleTypeChambre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeChambre>>): void {
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
