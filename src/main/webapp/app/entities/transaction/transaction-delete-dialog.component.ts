import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';

@Component({
  templateUrl: './transaction-delete-dialog.component.html',
})
export class TransactionDeleteDialogComponent {
  transaction?: ITransaction;

  constructor(
    protected transactionService: TransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionListModification');
      this.activeModal.close();
    });
  }
}
