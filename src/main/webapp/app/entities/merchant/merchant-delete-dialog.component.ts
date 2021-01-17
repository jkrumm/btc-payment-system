import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMerchant } from 'app/shared/model/merchant.model';
import { MerchantService } from './merchant.service';

@Component({
  templateUrl: './merchant-delete-dialog.component.html',
})
export class MerchantDeleteDialogComponent {
  merchant?: IMerchant;

  constructor(protected merchantService: MerchantService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.merchantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('merchantListModification');
      this.activeModal.close();
    });
  }
}
