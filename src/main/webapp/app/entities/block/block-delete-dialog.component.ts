import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlock } from 'app/shared/model/block.model';
import { BlockService } from './block.service';

@Component({
  templateUrl: './block-delete-dialog.component.html',
})
export class BlockDeleteDialogComponent {
  block?: IBlock;

  constructor(protected blockService: BlockService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.blockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('blockListModification');
      this.activeModal.close();
    });
  }
}
