import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvisHebergement } from 'app/shared/model/avis-hebergement.model';

@Component({
  selector: 'jhi-avis-hebergement-detail',
  templateUrl: './avis-hebergement-detail.component.html',
})
export class AvisHebergementDetailComponent implements OnInit {
  avisHebergement: IAvisHebergement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisHebergement }) => (this.avisHebergement = avisHebergement));
  }

  previousState(): void {
    window.history.back();
  }
}
