import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifHebergment } from 'app/shared/model/tarif-hebergment.model';

@Component({
  selector: 'jhi-tarif-hebergment-detail',
  templateUrl: './tarif-hebergment-detail.component.html',
})
export class TarifHebergmentDetailComponent implements OnInit {
  tarifHebergment: ITarifHebergment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifHebergment }) => (this.tarifHebergment = tarifHebergment));
  }

  previousState(): void {
    window.history.back();
  }
}
