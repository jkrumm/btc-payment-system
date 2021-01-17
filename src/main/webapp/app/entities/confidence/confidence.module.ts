import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BtcPaymentSystemSharedModule } from 'app/shared/shared.module';
import { ConfidenceComponent } from './confidence.component';
import { ConfidenceDetailComponent } from './confidence-detail.component';
import { ConfidenceUpdateComponent } from './confidence-update.component';
import { ConfidenceDeleteDialogComponent } from './confidence-delete-dialog.component';
import { confidenceRoute } from './confidence.route';

@NgModule({
  imports: [BtcPaymentSystemSharedModule, RouterModule.forChild(confidenceRoute)],
  declarations: [ConfidenceComponent, ConfidenceDetailComponent, ConfidenceUpdateComponent, ConfidenceDeleteDialogComponent],
  entryComponents: [ConfidenceDeleteDialogComponent],
})
export class BtcPaymentSystemConfidenceModule {}
