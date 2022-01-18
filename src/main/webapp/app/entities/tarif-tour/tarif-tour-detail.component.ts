import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifTour } from 'app/shared/model/tarif-tour.model';

@Component({
  selector: 'jhi-tarif-tour-detail',
  templateUrl: './tarif-tour-detail.component.html',
})
export class TarifTourDetailComponent implements OnInit {
  tarifTour: ITarifTour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTour }) => (this.tarifTour = tarifTour));
  }

  previousState(): void {
    window.history.back();
  }
}
