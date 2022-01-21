import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAvisTourMedia, AvisTourMedia } from 'app/shared/model/avis-tour-media.model';
import { AvisTourMediaService } from './avis-tour-media.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAvisTour } from 'app/shared/model/avis-tour.model';
import { AvisTourService } from 'app/entities/avis-tour/avis-tour.service';

@Component({
  selector: 'jhi-avis-tour-media-update',
  templateUrl: './avis-tour-media-update.component.html',
})
export class AvisTourMediaUpdateComponent implements OnInit {
  isSaving = false;
  avistours: IAvisTour[] = [];

  editForm = this.fb.group({
    id: [],
    mediaType: [],
    mediaUrl: [],
    mediacontent: [],
    mediacontentContentType: [],
    score: [],
    avisTour: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected avisTourMediaService: AvisTourMediaService,
    protected avisTourService: AvisTourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTourMedia }) => {
      this.updateForm(avisTourMedia);

      this.avisTourService.query().subscribe((res: HttpResponse<IAvisTour[]>) => (this.avistours = res.body || []));
    });
  }

  updateForm(avisTourMedia: IAvisTourMedia): void {
    this.editForm.patchValue({
      id: avisTourMedia.id,
      mediaType: avisTourMedia.mediaType,
      mediaUrl: avisTourMedia.mediaUrl,
      mediacontent: avisTourMedia.mediacontent,
      mediacontentContentType: avisTourMedia.mediacontentContentType,
      score: avisTourMedia.score,
      avisTour: avisTourMedia.avisTour,
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
    const avisTourMedia = this.createFromForm();
    if (avisTourMedia.id !== undefined) {
      this.subscribeToSaveResponse(this.avisTourMediaService.update(avisTourMedia));
    } else {
      this.subscribeToSaveResponse(this.avisTourMediaService.create(avisTourMedia));
    }
  }

  private createFromForm(): IAvisTourMedia {
    return {
      ...new AvisTourMedia(),
      id: this.editForm.get(['id'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      mediaUrl: this.editForm.get(['mediaUrl'])!.value,
      mediacontentContentType: this.editForm.get(['mediacontentContentType'])!.value,
      mediacontent: this.editForm.get(['mediacontent'])!.value,
      score: this.editForm.get(['score'])!.value,
      avisTour: this.editForm.get(['avisTour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisTourMedia>>): void {
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

  trackById(index: number, item: IAvisTour): any {
    return item.id;
  }
}
