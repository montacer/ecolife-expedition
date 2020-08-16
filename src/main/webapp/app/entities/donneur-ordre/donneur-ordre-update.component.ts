import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDonneurOrdre, DonneurOrdre } from 'app/shared/model/donneur-ordre.model';
import { DonneurOrdreService } from './donneur-ordre.service';

@Component({
  selector: 'jhi-donneur-ordre-update',
  templateUrl: './donneur-ordre-update.component.html',
})
export class DonneurOrdreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    adresse: [],
    numTelephone: [],
    email: [],
  });

  constructor(protected donneurOrdreService: DonneurOrdreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donneurOrdre }) => {
      this.updateForm(donneurOrdre);
    });
  }

  updateForm(donneurOrdre: IDonneurOrdre): void {
    this.editForm.patchValue({
      id: donneurOrdre.id,
      nom: donneurOrdre.nom,
      prenom: donneurOrdre.prenom,
      adresse: donneurOrdre.adresse,
      numTelephone: donneurOrdre.numTelephone,
      email: donneurOrdre.email,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donneurOrdre = this.createFromForm();
    if (donneurOrdre.id !== undefined) {
      this.subscribeToSaveResponse(this.donneurOrdreService.update(donneurOrdre));
    } else {
      this.subscribeToSaveResponse(this.donneurOrdreService.create(donneurOrdre));
    }
  }

  private createFromForm(): IDonneurOrdre {
    return {
      ...new DonneurOrdre(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      numTelephone: this.editForm.get(['numTelephone'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonneurOrdre>>): void {
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
