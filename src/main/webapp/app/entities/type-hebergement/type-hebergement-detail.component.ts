import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';

@Component({
  selector: 'jhi-type-hebergement-detail',
  templateUrl: './type-hebergement-detail.component.html',
})
export class TypeHebergementDetailComponent implements OnInit {
  typeHebergement: ITypeHebergement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeHebergement }) => (this.typeHebergement = typeHebergement));
  }

  previousState(): void {
    window.history.back();
  }
}
