import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegion, Region } from 'app/shared/model/region.model';
import { RegionService } from './region.service';
import { IPays } from 'app/shared/model/pays.model';
import { PaysService } from 'app/entities/pays/pays.service';
import { ITypeRegion } from 'app/shared/model/type-region.model';
import { TypeRegionService } from 'app/entities/type-region/type-region.service';

type SelectableEntity = IPays | ITypeRegion;

@Component({
  selector: 'jhi-region-update',
  templateUrl: './region-update.component.html',
})
export class RegionUpdateComponent implements OnInit {
  isSaving = false;
  pays: IPays[] = [];
  typeregions: ITypeRegion[] = [];

  editForm = this.fb.group({
    id: [],
    libRegion: [],
    pays: [],
    typeRegion: [],
  });

  constructor(
    protected regionService: RegionService,
    protected paysService: PaysService,
    protected typeRegionService: TypeRegionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ region }) => {
      this.updateForm(region);

      this.paysService.query().subscribe((res: HttpResponse<IPays[]>) => (this.pays = res.body || []));

      this.typeRegionService.query().subscribe((res: HttpResponse<ITypeRegion[]>) => (this.typeregions = res.body || []));
    });
  }

  updateForm(region: IRegion): void {
    this.editForm.patchValue({
      id: region.id,
      libRegion: region.libRegion,
      pays: region.pays,
      typeRegion: region.typeRegion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const region = this.createFromForm();
    if (region.id !== undefined) {
      this.subscribeToSaveResponse(this.regionService.update(region));
    } else {
      this.subscribeToSaveResponse(this.regionService.create(region));
    }
  }

  private createFromForm(): IRegion {
    return {
      ...new Region(),
      id: this.editForm.get(['id'])!.value,
      libRegion: this.editForm.get(['libRegion'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      typeRegion: this.editForm.get(['typeRegion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegion>>): void {
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
