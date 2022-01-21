import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifTransfert, TarifTransfert } from 'app/shared/model/tarif-transfert.model';
import { TarifTransfertService } from './tarif-transfert.service';
import { ITypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from 'app/entities/type-transfert/type-transfert.service';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';

type SelectableEntity = ITypeTransfert | ITypeTarif;

@Component({
  selector: 'jhi-tarif-transfert-update',
  templateUrl: './tarif-transfert-update.component.html',
})
export class TarifTransfertUpdateComponent implements OnInit {
  isSaving = false;
  typetransferts: ITypeTransfert[] = [];
  typetarifs: ITypeTarif[] = [];

  editForm = this.fb.group({
    id: [],
    montantTTC: [],
    typeTransfert: [],
    typeTarif: [],
  });

  constructor(
    protected tarifTransfertService: TarifTransfertService,
    protected typeTransfertService: TypeTransfertService,
    protected typeTarifService: TypeTarifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransfert }) => {
      this.updateForm(tarifTransfert);

      this.typeTransfertService.query().subscribe((res: HttpResponse<ITypeTransfert[]>) => (this.typetransferts = res.body || []));

      this.typeTarifService.query().subscribe((res: HttpResponse<ITypeTarif[]>) => (this.typetarifs = res.body || []));
    });
  }

  updateForm(tarifTransfert: ITarifTransfert): void {
    this.editForm.patchValue({
      id: tarifTransfert.id,
      montantTTC: tarifTransfert.montantTTC,
      typeTransfert: tarifTransfert.typeTransfert,
      typeTarif: tarifTransfert.typeTarif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifTransfert = this.createFromForm();
    if (tarifTransfert.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifTransfertService.update(tarifTransfert));
    } else {
      this.subscribeToSaveResponse(this.tarifTransfertService.create(tarifTransfert));
    }
  }

  private createFromForm(): ITarifTransfert {
    return {
      ...new TarifTransfert(),
      id: this.editForm.get(['id'])!.value,
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      typeTransfert: this.editForm.get(['typeTransfert'])!.value,
      typeTarif: this.editForm.get(['typeTarif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifTransfert>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
