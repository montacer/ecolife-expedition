import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from 'app/entities/reservation/reservation.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  reservations: IReservation[] = [];
  dateNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    adresse: [],
    email: [],
    dateNaissance: [],
    reservation: [],
  });

  constructor(
    protected clientService: ClientService,
    protected reservationService: ReservationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.reservationService.query().subscribe((res: HttpResponse<IReservation[]>) => (this.reservations = res.body || []));
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      nom: client.nom,
      prenom: client.prenom,
      adresse: client.adresse,
      email: client.email,
      dateNaissance: client.dateNaissance,
      reservation: client.reservation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      reservation: this.editForm.get(['reservation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  trackById(index: number, item: IReservation): any {
    return item.id;
  }
}
