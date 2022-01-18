import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifServiceSupplementaire, TarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';
import { TarifServiceSupplementaireService } from './tarif-service-supplementaire.service';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';

@Component({
  selector: 'jhi-tarif-service-supplementaire-update',
  templateUrl: './tarif-service-supplementaire-update.component.html',
})
export class TarifServiceSupplementaireUpdateComponent implements OnInit {
  isSaving = false;
  typetarifs: ITypeTarif[] = [];

  editForm = this.fb.group({
    id: [],
    montantTTC: [],
    typeTarif: [],
  });

  constructor(
    protected tarifServiceSupplementaireService: TarifServiceSupplementaireService,
    protected typeTarifService: TypeTarifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifServiceSupplementaire }) => {
      this.updateForm(tarifServiceSupplementaire);

      this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typetarifs = res.body || []));
    });
  }

  updateForm(tarifServiceSupplementaire: ITarifServiceSupplementaire): void {
    this.editForm.patchValue({
      id: tarifServiceSupplementaire.id,
      montantTTC: tarifServiceSupplementaire.montantTTC,
      typeTarif: tarifServiceSupplementaire.typeTarif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifServiceSupplementaire = this.createFromForm();
    if (tarifServiceSupplementaire.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifServiceSupplementaireService.update(tarifServiceSupplementaire));
    } else {
      this.subscribeToSaveResponse(this.tarifServiceSupplementaireService.create(tarifServiceSupplementaire));
    }
  }

  private createFromForm(): ITarifServiceSupplementaire {
    return {
      ...new TarifServiceSupplementaire(),
      id: this.editForm.get(['id'])!.value,
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      typeTarif: this.editForm.get(['typeTarif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifServiceSupplementaire>>): void {
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
