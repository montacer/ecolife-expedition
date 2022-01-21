import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeTransfert } from 'app/shared/model/type-transfert.model';
import { TypeTransfertService } from './type-transfert.service';
import { TypeTransfertDeleteDialogComponent } from './type-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-type-transfert',
  templateUrl: './type-transfert.component.html',
})
export class TypeTransfertComponent implements OnInit, OnDestroy {
  typeTransferts?: ITypeTransfert[];
  eventSubscriber?: Subscription;

  constructor(
    protected typeTransfertService: TypeTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.typeTransfertService.query().subscribe((res: HttpResponse<ITypeTransfert[]>) => (this.typeTransferts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeTransferts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeTransfert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeTransferts(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeTransfertListModification', () => this.loadAll());
  }

  delete(typeTransfert: ITypeTransfert): void {
    const modalRef = this.modalService.open(TypeTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeTransfert = typeTransfert;
  }
}
