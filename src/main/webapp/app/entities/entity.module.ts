import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.BtcPaymentSystemTransactionModule),
      },
      {
        path: 'confidence',
        loadChildren: () => import('./confidence/confidence.module').then(m => m.BtcPaymentSystemConfidenceModule),
      },
      {
        path: 'merchant',
        loadChildren: () => import('./merchant/merchant.module').then(m => m.BtcPaymentSystemMerchantModule),
      },
      {
        path: 'fee',
        loadChildren: () => import('./fee/fee.module').then(m => m.BtcPaymentSystemFeeModule),
      },
      {
        path: 'block',
        loadChildren: () => import('./block/block.module').then(m => m.BtcPaymentSystemBlockModule),
      },
      {
        path: 'merchant-user',
        loadChildren: () => import('./merchant-user/merchant-user.module').then(m => m.BtcPaymentSystemMerchantUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BtcPaymentSystemEntityModule {}
