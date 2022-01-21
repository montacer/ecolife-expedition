import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvisHebergement, AvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { AvisHebergementService } from './avis-hebergement.service';
import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from 'app/entities/hebergement/hebergement.service';

@Component({
  selector: 'jhi-avis-hebergement-update',
  templateUrl: './avis-hebergement-update.component.html',
})
export class AvisHebergementUpdateComponent implements OnInit {
  isSaving = false;
  hebergements: IHebergement[] = [];

  editForm = this.fb.group({
    id: [],
    author: [],
    nbreEtoile: [],
    commentaire: [],
    hebergement: [],
  });

  constructor(
    protected avisHebergementService: AvisHebergementService,
    protected hebergementService: HebergementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisHebergement }) => {
      this.updateForm(avisHebergement);

      this.hebergementService.query().subscribe((res: HttpResponse<IHebergement[]>) => (this.hebergements = res.body || []));
    });
  }

  updateForm(avisHebergement: IAvisHebergement): void {
    this.editForm.patchValue({
      id: avisHebergement.id,
      author: avisHebergement.author,
      nbreEtoile: avisHebergement.nbreEtoile,
      commentaire: avisHebergement.commentaire,
      hebergement: avisHebergement.hebergement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisHebergement = this.createFromForm();
    if (avisHebergement.id !== undefined) {
      this.subscribeToSaveResponse(this.avisHebergementService.update(avisHebergement));
    } else {
      this.subscribeToSaveResponse(this.avisHebergementService.create(avisHebergement));
    }
  }

  private createFromForm(): IAvisHebergement {
    return {
      ...new AvisHebergement(),
      id: this.editForm.get(['id'])!.value,
      author: this.editForm.get(['author'])!.value,
      nbreEtoile: this.editForm.get(['nbreEtoile'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      hebergement: this.editForm.get(['hebergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisHebergement>>): void {
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

  trackById(index: number, item: IHebergement): any {
    return item.id;
  }
}
