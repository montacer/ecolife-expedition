import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IHotelMedia, HotelMedia } from 'app/shared/model/hotel-media.model';
import { HotelMediaService } from './hotel-media.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from 'app/entities/hotel/hotel.service';

@Component({
  selector: 'jhi-hotel-media-update',
  templateUrl: './hotel-media-update.component.html',
})
export class HotelMediaUpdateComponent implements OnInit {
  isSaving = false;
  hotels: IHotel[] = [];

  editForm = this.fb.group({
    id: [],
    mediaUrl: [],
    mediaType: [],
    mediacontent: [],
    mediacontentContentType: [],
    hotel: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected hotelMediaService: HotelMediaService,
    protected hotelService: HotelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelMedia }) => {
      this.updateForm(hotelMedia);

      this.hotelService.query().subscribe((res: HttpResponse<IHotel[]>) => (this.hotels = res.body || []));
    });
  }

  updateForm(hotelMedia: IHotelMedia): void {
    this.editForm.patchValue({
      id: hotelMedia.id,
      mediaUrl: hotelMedia.mediaUrl,
      mediaType: hotelMedia.mediaType,
      mediacontent: hotelMedia.mediacontent,
      mediacontentContentType: hotelMedia.mediacontentContentType,
      hotel: hotelMedia.hotel,
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
    const hotelMedia = this.createFromForm();
    if (hotelMedia.id !== undefined) {
      this.subscribeToSaveResponse(this.hotelMediaService.update(hotelMedia));
    } else {
      this.subscribeToSaveResponse(this.hotelMediaService.create(hotelMedia));
    }
  }

  private createFromForm(): IHotelMedia {
    return {
      ...new HotelMedia(),
      id: this.editForm.get(['id'])!.value,
      mediaUrl: this.editForm.get(['mediaUrl'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      mediacontentContentType: this.editForm.get(['mediacontentContentType'])!.value,
      mediacontent: this.editForm.get(['mediacontent'])!.value,
      hotel: this.editForm.get(['hotel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelMedia>>): void {
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

  trackById(index: number, item: IHotel): any {
    return item.id;
  }
}
