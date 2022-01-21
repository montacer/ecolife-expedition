import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAvisHebergementMedia, AvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';
import { AvisHebergementMediaService } from './avis-hebergement-media.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';
import { AvisHebergementService } from 'app/entities/avis-hebergement/avis-hebergement.service';

@Component({
  selector: 'jhi-avis-hebergement-media-update',
  templateUrl: './avis-hebergement-media-update.component.html',
})
export class AvisHebergementMediaUpdateComponent implements OnInit {
  isSaving = false;
  avishebergements: IAvisHebergement[] = [];

  editForm = this.fb.group({
    id: [],
    mediaType: [],
    mediaUrl: [],
    mediacontent: [],
    mediacontentContentType: [],
    avisHebergement: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected avisHebergementMediaService: AvisHebergementMediaService,
    protected avisHebergementService: AvisHebergementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisHebergementMedia }) => {
      this.updateForm(avisHebergementMedia);

      this.avisHebergementService.query().subscribe((res: HttpResponse<IAvisHebergement[]>) => (this.avishebergements = res.body || []));
    });
  }

  updateForm(avisHebergementMedia: IAvisHebergementMedia): void {
    this.editForm.patchValue({
      id: avisHebergementMedia.id,
      mediaType: avisHebergementMedia.mediaType,
      mediaUrl: avisHebergementMedia.mediaUrl,
      mediacontent: avisHebergementMedia.mediacontent,
      mediacontentContentType: avisHebergementMedia.mediacontentContentType,
      avisHebergement: avisHebergementMedia.avisHebergement,
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
    const avisHebergementMedia = this.createFromForm();
    if (avisHebergementMedia.id !== undefined) {
      this.subscribeToSaveResponse(this.avisHebergementMediaService.update(avisHebergementMedia));
    } else {
      this.subscribeToSaveResponse(this.avisHebergementMediaService.create(avisHebergementMedia));
    }
  }

  private createFromForm(): IAvisHebergementMedia {
    return {
      ...new AvisHebergementMedia(),
      id: this.editForm.get(['id'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      mediaUrl: this.editForm.get(['mediaUrl'])!.value,
      mediacontentContentType: this.editForm.get(['mediacontentContentType'])!.value,
      mediacontent: this.editForm.get(['mediacontent'])!.value,
      avisHebergement: this.editForm.get(['avisHebergement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisHebergementMedia>>): void {
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

  trackById(index: number, item: IAvisHebergement): any {
    return item.id;
  }
}
