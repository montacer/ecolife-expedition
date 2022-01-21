import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeTransfert } from 'app/shared/model/type-transfert.model';

@Component({
  selector: 'jhi-type-transfert-detail',
  templateUrl: './type-transfert-detail.component.html',
})
export class TypeTransfertDetailComponent implements OnInit {
  typeTransfert: ITypeTransfert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeTransfert }) => (this.typeTransfert = typeTransfert));
  }

  previousState(): void {
    window.history.back();
  }
}
