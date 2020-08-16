import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';
import { TypeChambreDeleteDialogComponent } from './type-chambre-delete-dialog.component';

@Component({
  selector: 'jhi-type-chambre',
  templateUrl: './type-chambre.component.html',
})
export class TypeChambreComponent implements OnInit, OnDestroy {
  typeChambres?: ITypeChambre[];
  eventSubscriber?: Subscription;

  constructor(
    protected typeChambreService: TypeChambreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.typeChambreService.query().subscribe((res: HttpResponse<ITypeChambre[]>) => (this.typeChambres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeChambres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeChambre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeChambres(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeChambreListModification', () => this.loadAll());
  }

  delete(typeChambre: ITypeChambre): void {
    const modalRef = this.modalService.open(TypeChambreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeChambre = typeChambre;
  }
}
