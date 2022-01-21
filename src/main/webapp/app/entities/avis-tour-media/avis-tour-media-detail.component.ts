import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAvisTourMedia } from 'app/shared/model/avis-tour-media.model';

@Component({
  selector: 'jhi-avis-tour-media-detail',
  templateUrl: './avis-tour-media-detail.component.html',
})
export class AvisTourMediaDetailComponent implements OnInit {
  avisTourMedia: IAvisTourMedia | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTourMedia }) => (this.avisTourMedia = avisTourMedia));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
