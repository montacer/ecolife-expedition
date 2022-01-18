import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITour, Tour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from 'app/entities/type-circuit/type-circuit.service';

type SelectableEntity = IRegion | ITypeCircuit;

@Component({
  selector: 'jhi-tour-update',
  templateUrl: './tour-update.component.html',
})
export class TourUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];
  typecircuits: ITypeCircuit[] = [];

  editForm = this.fb.group({
    id: [],
    libTitre: [],
    imageUrl: [],
    videoUrl: [],
    imageContent: [],
    imageContentContentType: [],
    videoContent: [],
    videoContentContentType: [],
    conseil: [],
    prixTTC: [],
    description: [],
    remise: [],
    prixRemise: [],
    starScore: [],
    duree: [],
    saison: [],
    status: [],
    region: [],
    typeCircuit: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tourService: TourService,
    protected regionService: RegionService,
    protected typeCircuitService: TypeCircuitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tour }) => {
      this.updateForm(tour);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.typeCircuitService.query().subscribe((res: HttpResponse<ITypeCircuit[]>) => (this.typecircuits = res.body || []));
    });
  }

  updateForm(tour: ITour): void {
    this.editForm.patchValue({
      id: tour.id,
      libTitre: tour.libTitre,
      imageUrl: tour.imageUrl,
      videoUrl: tour.videoUrl,
      imageContent: tour.imageContent,
      imageContentContentType: tour.imageContentContentType,
      videoContent: tour.videoContent,
      videoContentContentType: tour.videoContentContentType,
      conseil: tour.conseil,
      prixTTC: tour.prixTTC,
      description: tour.description,
      remise: tour.remise,
      prixRemise: tour.prixRemise,
      starScore: tour.starScore,
      duree: tour.duree,
      saison: tour.saison,
      status: tour.status,
      region: tour.region,
      typeCircuit: tour.typeCircuit,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ecoLifeExpeditionApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tour = this.createFromForm();
    if (tour.id !== undefined) {
      this.subscribeToSaveResponse(this.tourService.update(tour));
    } else {
      this.subscribeToSaveResponse(this.tourService.create(tour));
    }
  }

  private createFromForm(): ITour {
    return {
      ...new Tour(),
      id: this.editForm.get(['id'])!.value,
      libTitre: this.editForm.get(['libTitre'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      imageContentContentType: this.editForm.get(['imageContentContentType'])!.value,
      imageContent: this.editForm.get(['imageContent'])!.value,
      videoContentContentType: this.editForm.get(['videoContentContentType'])!.value,
      videoContent: this.editForm.get(['videoContent'])!.value,
      conseil: this.editForm.get(['conseil'])!.value,
      prixTTC: this.editForm.get(['prixTTC'])!.value,
      description: this.editForm.get(['description'])!.value,
      remise: this.editForm.get(['remise'])!.value,
      prixRemise: this.editForm.get(['prixRemise'])!.value,
      starScore: this.editForm.get(['starScore'])!.value,
      duree: this.editForm.get(['duree'])!.value,
      saison: this.editForm.get(['saison'])!.value,
      status: this.editForm.get(['status'])!.value,
      region: this.editForm.get(['region'])!.value,
      typeCircuit: this.editForm.get(['typeCircuit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITour>>): void {
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
