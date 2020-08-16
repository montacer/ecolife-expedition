import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';

@Component({
  selector: 'jhi-service-supplementaire-detail',
  templateUrl: './service-supplementaire-detail.component.html',
})
export class ServiceSupplementaireDetailComponent implements OnInit {
  serviceSupplementaire: IServiceSupplementaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceSupplementaire }) => (this.serviceSupplementaire = serviceSupplementaire));
  }

  previousState(): void {
    window.history.back();
  }
}
