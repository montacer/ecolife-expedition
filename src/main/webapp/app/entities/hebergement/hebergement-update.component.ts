import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHebergement, Hebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';
import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';
import { TypeHebergementService } from 'app/entities/type-hebergement/type-hebergement.service';

@Component({
  selector: 'jhi-hebergement-update',
  templateUrl: './hebergement-update.component.html',
})
export class HebergementUpdateComponent implements OnInit {
  isSaving = false;
  typehebergements: ITypeHebergement[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    adresse: [],
    lattitude: [],
    longitude: [],
    montantTTc: [],
    typeHebergement: [],
  });

  constructor(
    protected hebergementService: HebergementService,
    protected typeHebergementService: TypeHebergementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hebergement }) => {
      this.updateForm(hebergement);

      this.typeHebergementService.query().subscribe((res: HttpResponse<ITypeHebergement[]>) => (this.typehebergements = res.body || []));
    });
  }

  updateForm(hebergement: IHebergement): void {
    this.editForm.patchValue({
      id: hebergement.id,
      description: hebergement.description,
      adresse: hebergement.adresse,
      lattitude: hebergement.lattitude,
      longitude: hebergement.longitude,
      montantTTc: hebergement.montantTTc,
      typeHebergement: hebergement.typeHebergement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hebergement = this.createFromForm();
    if (hebergement.id !== undefined) {
      this.subscribeToSaveResponse(this.hebergementService.update(hebergement));
    } else {
      this.subscribeToSaveResponse(this.hebergementService.create(hebergement));
    }
  }

  private createFromForm(): IHebergement {
    return {
      ...new Hebergement(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      lattitude: this.editForm.get(['lattitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      montantTTc: this.editForm.get(['montantTTc'])!.value,
      typeHebergement: this.editForm.get(['typeHebergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHebergement>>): void {
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

  trackById(index: number, item: ITypeHebergement): any {
    return item.id;
  }
}
