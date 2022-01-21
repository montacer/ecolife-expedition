import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifTransfert } from 'app/shared/model/tarif-transfert.model';

@Component({
  selector: 'jhi-tarif-transfert-detail',
  templateUrl: './tarif-transfert-detail.component.html',
})
export class TarifTransfertDetailComponent implements OnInit {
  tarifTransfert: ITarifTransfert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransfert }) => (this.tarifTransfert = tarifTransfert));
  }

  previousState(): void {
    window.history.back();
  }
}
