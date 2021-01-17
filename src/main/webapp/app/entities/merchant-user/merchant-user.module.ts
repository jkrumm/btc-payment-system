import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { MerchantUserComponent } from './merchant-user.component';
import { MerchantUserDetailComponent } from './merchant-user-detail.component';
import { MerchantUserUpdateComponent } from './merchant-user-update.component';
import { MerchantUserDeleteDialogComponent } from './merchant-user-delete-dialog.component';
import { merchantUserRoute } from './merchant-user.route';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(merchantUserRoute)],
  declarations: [MerchantUserComponent, MerchantUserDetailComponent, MerchantUserUpdateComponent, MerchantUserDeleteDialogComponent],
  entryComponents: [MerchantUserDeleteDialogComponent],
})
export class BtcPaymentSystemMerchantUserModule {}
