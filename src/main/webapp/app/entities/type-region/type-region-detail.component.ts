import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeRegion } from 'app/shared/model/type-region.model';

@Component({
  selector: 'jhi-type-region-detail',
  templateUrl: './type-region-detail.component.html',
})
export class TypeRegionDetailComponent implements OnInit {
  typeRegion: ITypeRegion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRegion }) => (this.typeRegion = typeRegion));
  }

  previousState(): void {
    window.history.back();
  }
}
