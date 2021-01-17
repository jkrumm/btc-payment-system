import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfidence } from 'app/shared/model/confidence.model';
import { ConfidenceService } from './confidence.service';

@Component({
  templateUrl: './confidence-delete-dialog.component.html',
})
export class ConfidenceDeleteDialogComponent {
  confidence?: IConfidence;

  constructor(
    protected confidenceService: ConfidenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.confidenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('confidenceListModification');
      this.activeModal.close();
    });
  }
}
