import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITourMedia } from 'app/shared/model/tour-media.model';

@Component({
  selector: 'jhi-tour-media-detail',
  templateUrl: './tour-media-detail.component.html',
})
export class TourMediaDetailComponent implements OnInit {
  tourMedia: ITourMedia | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tourMedia }) => (this.tourMedia = tourMedia));
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
