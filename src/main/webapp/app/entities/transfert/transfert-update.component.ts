import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransfert, Transfert } from 'app/shared/model/transfert.model';
import { TransfertService } from './transfert.service';
import { ITypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from 'app/entities/type-transfert/type-transfert.service';

@Component({
  selector: 'jhi-transfert-update',
  templateUrl: './transfert-update.component.html',
})
export class TransfertUpdateComponent implements OnInit {
  isSaving = false;
  typetransferts: ITypeTransfert[] = [];

  editForm = this.fb.group({
    id: [],
    montantTTC: [],
    typeTransfert: [],
  });

  constructor(
    protected transfertService: TransfertService,
    protected typeTransfertService: TypeTransfertService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transfert }) => {
      this.updateForm(transfert);

      this.typeTransfertService.query().subscribe((res: HttpResponse<ITypeTransfert[]>) => (this.typetransferts = res.body || []));
    });
  }

  updateForm(transfert: ITransfert): void {
    this.editForm.patchValue({
      id: transfert.id,
      montantTTC: transfert.montantTTC,
      typeTransfert: transfert.typeTransfert,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transfert = this.createFromForm();
    if (transfert.id !== undefined) {
      this.subscribeToSaveResponse(this.transfertService.update(transfert));
    } else {
      this.subscribeToSaveResponse(this.transfertService.create(transfert));
    }
  }

  private createFromForm(): ITransfert {
    return {
      ...new Transfert(),
      id: this.editForm.get(['id'])!.value,
      montantTTC: this.editForm.get(['montantTTC'])!.value,
      typeTransfert: this.editForm.get(['typeTransfert'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransfert>>): void {
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

  trackById(index: number, item: ITypeTransfert): any {
    return item.id;
  }
}
