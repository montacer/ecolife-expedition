import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITourMedia, TourMedia } from 'app/shared/model/tour-media.model';
import { TourMediaService } from './tour-media.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITour } from 'app/shared/model/tour.model';
import { TourService } from 'app/entities/tour/tour.service';

@Component({
  selector: 'jhi-tour-media-update',
  templateUrl: './tour-media-update.component.html',
})
export class TourMediaUpdateComponent implements OnInit {
  isSaving = false;
  tours: ITour[] = [];

  editForm = this.fb.group({
    id: [],
    mediaUrl: [],
    mediaType: [],
    mediacontent: [],
    mediacontentContentType: [],
    tour: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tourMediaService: TourMediaService,
    protected tourService: TourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tourMedia }) => {
      this.updateForm(tourMedia);

      this.tourService.query().subscribe((res: HttpResponse<ITour[]>) => (this.tours = res.body || []));
    });
  }

  updateForm(tourMedia: ITourMedia): void {
    this.editForm.patchValue({
      id: tourMedia.id,
      mediaUrl: tourMedia.mediaUrl,
      mediaType: tourMedia.mediaType,
      mediacontent: tourMedia.mediacontent,
      mediacontentContentType: tourMedia.mediacontentContentType,
      tour: tourMedia.tour,
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
    const tourMedia = this.createFromForm();
    if (tourMedia.id !== undefined) {
      this.subscribeToSaveResponse(this.tourMediaService.update(tourMedia));
    } else {
      this.subscribeToSaveResponse(this.tourMediaService.create(tourMedia));
    }
  }

  private createFromForm(): ITourMedia {
    return {
      ...new TourMedia(),
      id: this.editForm.get(['id'])!.value,
      mediaUrl: this.editForm.get(['mediaUrl'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      mediacontentContentType: this.editForm.get(['mediacontentContentType'])!.value,
      mediacontent: this.editForm.get(['mediacontent'])!.value,
      tour: this.editForm.get(['tour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITourMedia>>): void {
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

  trackById(index: number, item: ITour): any {
    return item.id;
  }
}
