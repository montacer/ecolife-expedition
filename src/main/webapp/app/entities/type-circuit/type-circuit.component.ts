import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { TypeCircuitService } from './type-circuit.service';
import { TypeCircuitDeleteDialogComponent } from './type-circuit-delete-dialog.component';

@Component({
  selector: 'jhi-type-circuit',
  templateUrl: './type-circuit.component.html',
})
export class TypeCircuitComponent implements OnInit, OnDestroy {
  typeCircuits?: ITypeCircuit[];
  eventSubscriber?: Subscription;

  constructor(
    protected typeCircuitService: TypeCircuitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.typeCircuitService.query().subscribe((res: HttpResponse<ITypeCircuit[]>) => (this.typeCircuits = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeCircuits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeCircuit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeCircuits(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeCircuitListModification', () => this.loadAll());
  }

  delete(typeCircuit: ITypeCircuit): void {
    const modalRef = this.modalService.open(TypeCircuitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeCircuit = typeCircuit;
  }
}
