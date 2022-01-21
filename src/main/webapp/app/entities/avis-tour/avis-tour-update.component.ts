import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvisTour, AvisTour } from 'app/shared/model/avis-tour.model';
import { AvisTourService } from './avis-tour.service';

@Component({
  selector: 'jhi-avis-tour-update',
  templateUrl: './avis-tour-update.component.html',
})
export class AvisTourUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    author: [],
    nbreEtoile: [],
    commentaire: [],
  });

  constructor(protected avisTourService: AvisTourService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTour }) => {
      this.updateForm(avisTour);
    });
  }

  updateForm(avisTour: IAvisTour): void {
    this.editForm.patchValue({
      id: avisTour.id,
      author: avisTour.author,
      nbreEtoile: avisTour.nbreEtoile,
      commentaire: avisTour.commentaire,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisTour = this.createFromForm();
    if (avisTour.id !== undefined) {
      this.subscribeToSaveResponse(this.avisTourService.update(avisTour));
    } else {
      this.subscribeToSaveResponse(this.avisTourService.create(avisTour));
    }
  }

  private createFromForm(): IAvisTour {
    return {
      ...new AvisTour(),
      id: this.editForm.get(['id'])!.value,
      author: this.editForm.get(['author'])!.value,
      nbreEtoile: this.editForm.get(['nbreEtoile'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisTour>>): void {
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
