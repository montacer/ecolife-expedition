import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifHebergement, TarifHebergement } from 'app/shared/model/tarif-hebergement.model';
import { TarifHebergementService } from './tarif-hebergement.service';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';

@Component({
  selector: 'jhi-tarif-hebergement-update',
  templateUrl: './tarif-hebergement-update.component.html',
})
export class TarifHebergementUpdateComponent implements OnInit {
  isSaving = false;
  typetarifs: ITypeTarif[] = [];

  editForm = this.fb.group({
    id: [],
    montantTTC: [],
    typeTarif: [],
  });

  constructor(
    protected tarifHebergementService: TarifHebergementService,
    protected typeTarifService: TypeTarifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifHebergement }) => {
      this.updateForm(tarifHebergement);

      this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typetarifs = res.body || []));
    });
  }

  updateForm(tarifHebergement: ITarifHebergement): void {
    this.editForm.patchValue({
      id: tarifHebergement.id,
      montantTTC: tarifHebergement.montantTTC,
      typeTarif: tarifHebergement.typeTarif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifHebergement = this.createFromForm();
    if (tarifHebergement.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifHebergementService.update(tarifHebergement));
    } else {
      this.subscribeToSaveResponse(this.tarifHebergementService.create(tarifHebergement));
    }
  }

  private createFromForm(): ITarifHebergement {
    return {
      ...new TarifHebergement(),
      id: this.editForm.get(['id'])!.value,
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      typeTarif: this.editForm.get(['typeTarif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifHebergement>>): void {
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

  trackById(index: number, item: ITypeTarif): any {
    return item.id;
  }
}
