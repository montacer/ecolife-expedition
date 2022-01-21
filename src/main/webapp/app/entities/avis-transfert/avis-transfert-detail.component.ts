import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvisTransfert } from 'app/shared/model/avis-transfert.model';

@Component({
  selector: 'jhi-avis-transfert-detail',
  templateUrl: './avis-transfert-detail.component.html',
})
export class AvisTransfertDetailComponent implements OnInit {
  avisTransfert: IAvisTransfert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisTransfert }) => (this.avisTransfert = avisTransfert));
  }

  previousState(): void {
    window.history.back();
  }
}
