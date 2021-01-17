import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFee } from 'app/shared/model/fee.model';
import { FeeService } from './fee.service';

@Component({
  templateUrl: './fee-delete-dialog.component.html',
})
export class FeeDeleteDialogComponent {
  fee?: IFee;

  constructor(protected feeService: FeeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('feeListModification');
      this.activeModal.close();
    });
  }
}
