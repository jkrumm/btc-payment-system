import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { TransactionComponent } from './transaction.component';
import { TransactionDetailComponent } from './transaction-detail.component';
import { TransactionUpdateComponent } from './transaction-update.component';
import { TransactionDeleteDialogComponent } from './transaction-delete-dialog.component';
import { transactionRoute } from './transaction.route';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(transactionRoute)],
  declarations: [TransactionComponent, TransactionDetailComponent, TransactionUpdateComponent, TransactionDeleteDialogComponent],
  entryComponents: [TransactionDeleteDialogComponent],
})
export class BtcPaymentSystemTransactionModule {}
