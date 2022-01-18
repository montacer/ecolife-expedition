import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifServiceSupplementaire } from 'app/shared/model/tarif-service-supplementaire.model';

@Component({
  selector: 'jhi-tarif-service-supplementaire-detail',
  templateUrl: './tarif-service-supplementaire-detail.component.html',
})
export class TarifServiceSupplementaireDetailComponent implements OnInit {
  tarifServiceSupplementaire: ITarifServiceSupplementaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifServiceSupplementaire }) => (this.tarifServiceSupplementaire = tarifServiceSupplementaire));
  }

  previousState(): void {
    window.history.back();
  }
}
