import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { BlockComponent } from './block.component';
import { BlockDetailComponent } from './block-detail.component';
import { BlockUpdateComponent } from './block-update.component';
import { BlockDeleteDialogComponent } from './block-delete-dialog.component';
import { blockRoute } from './block.route';
import { BlockLatestComponent } from 'app/entities/block/block-latest.component';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(blockRoute)],
  declarations: [BlockComponent, BlockDetailComponent, BlockLatestComponent, BlockUpdateComponent, BlockDeleteDialogComponent],
  entryComponents: [BlockDeleteDialogComponent],
})
export class BtcPaymentSystemBlockModule {}
