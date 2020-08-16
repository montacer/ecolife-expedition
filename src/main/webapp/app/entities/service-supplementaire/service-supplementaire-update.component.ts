import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServiceSupplementaire, ServiceSupplementaire } from 'app/shared/model/service-supplementaire.model';
import { ServiceSupplementaireService } from './service-supplementaire.service';
import { ITour } from 'app/shared/model/tour.model';
import { TourService } from 'app/entities/tour/tour.service';

@Component({
  selector: 'jhi-service-supplementaire-update',
  templateUrl: './service-supplementaire-update.component.html',
})
export class ServiceSupplementaireUpdateComponent implements OnInit {
  isSaving = false;
  tours: ITour[] = [];

  editForm = this.fb.group({
    id: [],
    libelleService: [],
    tour: [],
  });

  constructor(
    protected serviceSupplementaireService: ServiceSupplementaireService,
    protected tourService: TourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceSupplementaire }) => {
      this.updateForm(serviceSupplementaire);

      this.tourService.query().subscribe((res: HttpResponse<ITour[]>) => (this.tours = res.body || []));
    });
  }

  updateForm(serviceSupplementaire: IServiceSupplementaire): void {
    this.editForm.patchValue({
      id: serviceSupplementaire.id,
      libelleService: serviceSupplementaire.libelleService,
      tour: serviceSupplementaire.tour,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceSupplementaire = this.createFromForm();
    if (serviceSupplementaire.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceSupplementaireService.update(serviceSupplementaire));
    } else {
      this.subscribeToSaveResponse(this.serviceSupplementaireService.create(serviceSupplementaire));
    }
  }

  private createFromForm(): IServiceSupplementaire {
    return {
      ...new ServiceSupplementaire(),
      id: this.editForm.get(['id'])!.value,
      libelleService: this.editForm.get(['libelleService'])!.value,
      tour: this.editForm.get(['tour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceSupplementaire>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITour): any {
    return item.id;
  }
}
