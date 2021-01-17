import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { MerchantComponent } from './merchant.component';
import { MerchantDetailComponent } from './merchant-detail.component';
import { MerchantUpdateComponent } from './merchant-update.component';
import { MerchantDeleteDialogComponent } from './merchant-delete-dialog.component';
import { merchantRoute } from './merchant.route';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(merchantRoute)],
  declarations: [MerchantComponent, MerchantDetailComponent, MerchantUpdateComponent, MerchantDeleteDialogComponent],
  entryComponents: [MerchantDeleteDialogComponent],
})
export class BtcPaymentSystemMerchantModule {}
