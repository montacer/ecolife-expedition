import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonneurOrdre } from 'app/shared/model/donneur-ordre.model';

@Component({
  selector: 'jhi-donneur-ordre-detail',
  templateUrl: './donneur-ordre-detail.component.html',
})
export class DonneurOrdreDetailComponent implements OnInit {
  donneurOrdre: IDonneurOrdre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donneurOrdre }) => (this.donneurOrdre = donneurOrdre));
  }

  previousState(): void {
    window.history.back();
  }
}
