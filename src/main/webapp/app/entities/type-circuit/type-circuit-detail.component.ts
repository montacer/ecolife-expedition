import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCircuit } from 'app/shared/model/type-circuit.model';

@Component({
  selector: 'jhi-type-circuit-detail',
  templateUrl: './type-circuit-detail.component.html',
})
export class TypeCircuitDetailComponent implements OnInit {
  typeCircuit: ITypeCircuit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCircuit }) => (this.typeCircuit = typeCircuit));
  }

  previousState(): void {
    window.history.back();
  }
}
