import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EcoLifeExpeditionTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ServiceSupplementaireDeleteDialogComponent } from 'app/entities/service-supplementaire/service-supplementaire-delete-dialog.component';
import { ServiceSupplementaireService } from 'app/entities/service-supplementaire/service-supplementaire.service';

describe('Component Tests', () => {
  describe('ServiceSupplementaire Management Delete Component', () => {
    let comp: ServiceSupplementaireDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceSupplementaireDeleteDialogComponent>;
    let service: ServiceSupplementaireService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EcoLifeExpeditionTestModule],
        declarations: [ServiceSupplementaireDeleteDialogComponent],
      })
        .overrideTemplate(ServiceSupplementaireDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceSupplementaireDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupplementaireService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
