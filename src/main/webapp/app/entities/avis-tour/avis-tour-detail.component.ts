import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvisTour } from 'app/shared/model/avis-tour.model';

@Component({
  selector: 'jhi-avis-tour-detail',
  templateUrl: './avis-tour-detail.component.html',
})
export class AvisTourDetailComponent implements OnInit {
  avisTour: IAvisTour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTour }) => (this.avisTour = avisTour));
  }

  previousState(): void {
    window.history.back();
  }
}
