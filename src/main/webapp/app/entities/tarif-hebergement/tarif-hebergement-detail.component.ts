import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifHebergement } from 'app/shared/model/tarif-hebergement.model';

@Component({
  selector: 'jhi-tarif-hebergement-detail',
  templateUrl: './tarif-hebergement-detail.component.html',
})
export class TarifHebergementDetailComponent implements OnInit {
  tarifHebergement: ITarifHebergement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifHebergement }) => (this.tarifHebergement = tarifHebergement));
  }

  previousState(): void {
    window.history.back();
  }
}
