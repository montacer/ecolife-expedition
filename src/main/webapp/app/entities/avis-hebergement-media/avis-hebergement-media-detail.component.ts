import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAvisHebergementMedia } from 'app/shared/model/avis-hebergement-media.model';

@Component({
  selector: 'jhi-avis-hebergement-media-detail',
  templateUrl: './avis-hebergement-media-detail.component.html',
})
export class AvisHebergementMediaDetailComponent implements OnInit {
  avisHebergementMedia: IAvisHebergementMedia | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisHebergementMedia }) => (this.avisHebergementMedia = avisHebergementMedia));
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
