import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgence, Agence } from 'app/shared/model/agence.model';
import { AgenceService } from './agence.service';

@Component({
  selector: 'jhi-agence-update',
  templateUrl: './agence-update.component.html',
})
export class AgenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libAgence: [],
    adresseAgence: [],
    contactAgence: [],
  });

  constructor(protected agenceService: AgenceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agence }) => {
      this.updateForm(agence);
    });
  }

  updateForm(agence: IAgence): void {
    this.editForm.patchValue({
      id: agence.id,
      libAgence: agence.libAgence,
      adresseAgence: agence.adresseAgence,
      contactAgence: agence.contactAgence,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agence = this.createFromForm();
    if (agence.id !== undefined) {
      this.subscribeToSaveResponse(this.agenceService.update(agence));
    } else {
      this.subscribeToSaveResponse(this.agenceService.create(agence));
    }
  }

  private createFromForm(): IAgence {
    return {
      ...new Agence(),
      id: this.editForm.get(['id'])!.value,
      libAgence: this.editForm.get(['libAgence'])!.value,
      adresseAgence: this.editForm.get(['adresseAgence'])!.value,
      contactAgence: this.editForm.get(['contactAgence'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgence>>): void {
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
