import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMerchantUser } from 'app/shared/model/merchant-user.model';
import { MerchantUserService } from './merchant-user.service';

@Component({
  templateUrl: './merchant-user-delete-dialog.component.html',
})
export class MerchantUserDeleteDialogComponent {
  merchantUser?: IMerchantUser;

  constructor(
    protected merchantUserService: MerchantUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.merchantUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('merchantUserListModification');
      this.activeModal.close();
    });
  }
}
