import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { FeeComponent } from './fee.component';
import { FeeDetailComponent } from './fee-detail.component';
import { FeeUpdateComponent } from './fee-update.component';
import { FeeDeleteDialogComponent } from './fee-delete-dialog.component';
import { feeRoute } from './fee.route';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(feeRoute)],
  declarations: [FeeComponent, FeeDetailComponent, FeeUpdateComponent, FeeDeleteDialogComponent],
  entryComponents: [FeeDeleteDialogComponent],
})
export class BtcPaymentSystemFeeModule {}
