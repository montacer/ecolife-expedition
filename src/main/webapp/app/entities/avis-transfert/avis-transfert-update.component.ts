import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvisTransfert, AvisTransfert } from 'app/shared/model/avis-transfert.model';
import { AvisTransfertService } from './avis-transfert.service';
import { ITransfert } from 'app/shared/model/transfert.model';
import { TransfertService } from 'app/entities/transfert/transfert.service';

@Component({
  selector: 'jhi-avis-transfert-update',
  templateUrl: './avis-transfert-update.component.html',
})
export class AvisTransfertUpdateComponent implements OnInit {
  isSaving = false;
  transferts: ITransfert[] = [];

  editForm = this.fb.group({
    id: [],
    author: [],
    nbreEtoile: [],
    commentaire: [],
    transfert: [],
  });

  constructor(
    protected avisTransfertService: AvisTransfertService,
    protected transfertService: TransfertService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTransfert }) => {
      this.updateForm(avisTransfert);

      this.transfertService.query().subscribe((res: HttpResponse<ITransfert[]>) => (this.transferts = res.body || []));
    });
  }

  updateForm(avisTransfert: IAvisTransfert): void {
    this.editForm.patchValue({
      id: avisTransfert.id,
      author: avisTransfert.author,
      nbreEtoile: avisTransfert.nbreEtoile,
      commentaire: avisTransfert.commentaire,
      transfert: avisTransfert.transfert,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisTransfert = this.createFromForm();
    if (avisTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.avisTransfertService.update(avisTransfert));
    } else {
      this.subscribeToSaveResponse(this.avisTransfertService.create(avisTransfert));
    }
  }

  private createFromForm(): IAvisTransfert {
    return {
      ...new AvisTransfert(),
      id: this.editForm.get(['id'])!.value,
      author: this.editForm.get(['author'])!.value,
      nbreEtoile: this.editForm.get(['nbreEtoile'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      transfert: this.editForm.get(['transfert'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisTransfert>>): void {
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

  trackById(index: number, item: ITransfert): any {
    return item.id;
  }
}
