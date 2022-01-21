import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeHebergement } from 'app/shared/model/type-hebergement.model';
import { TypeHebergementService } from './type-hebergement.service';
import { TypeHebergementDeleteDialogComponent } from './type-hebergement-delete-dialog.component';

@Component({
  selector: 'jhi-type-hebergement',
  templateUrl: './type-hebergement.component.html',
})
export class TypeHebergementComponent implements OnInit, OnDestroy {
  typeHebergements?: ITypeHebergement[];
  eventSubscriber?: Subscription;

  constructor(
    protected typeHebergementService: TypeHebergementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.typeHebergementService.query().subscribe((res: HttpResponse<ITypeHebergement[]>) => (this.typeHebergements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeHebergements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeHebergement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeHebergements(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeHebergementListModification', () => this.loadAll());
  }

  delete(typeHebergement: ITypeHebergement): void {
    const modalRef = this.modalService.open(TypeHebergementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeHebergement = typeHebergement;
  }
}
