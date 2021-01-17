import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBlock, Block } from 'app/shared/model/block.model';
import { BlockService } from './block.service';

@Component({
  selector: 'jhi-block-update',
  templateUrl: './block-update.component.html',
})
export class BlockUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    minedAt: [null, [Validators.required]],
    block: [null, []],
    available: [null, [Validators.required]],
    estimated: [null, [Validators.required]],
    availableSpendable: [null, [Validators.required]],
    estimatedSpendable: [null, [Validators.required]],
  });

  constructor(protected blockService: BlockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ block }) => {
      if (!block.id) {
        const today = moment().startOf('day');
        block.minedAt = today;
      }

      this.updateForm(block);
    });
  }

  updateForm(block: IBlock): void {
    this.editForm.patchValue({
      id: block.id,
      minedAt: block.minedAt ? block.minedAt.format(DATE_TIME_FORMAT) : null,
      block: block.block,
      available: block.available,
      estimated: block.estimated,
      availableSpendable: block.availableSpendable,
      estimatedSpendable: block.estimatedSpendable,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const block = this.createFromForm();
    if (block.id !== undefined) {
      this.subscribeToSaveResponse(this.blockService.update(block));
    } else {
      this.subscribeToSaveResponse(this.blockService.create(block));
    }
  }

  private createFromForm(): IBlock {
    return {
      ...new Block(),
      id: this.editForm.get(['id'])!.value,
      minedAt: this.editForm.get(['minedAt'])!.value ? moment(this.editForm.get(['minedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      block: this.editForm.get(['block'])!.value,
      available: this.editForm.get(['available'])!.value,
      estimated: this.editForm.get(['estimated'])!.value,
      availableSpendable: this.editForm.get(['availableSpendable'])!.value,
      estimatedSpendable: this.editForm.get(['estimatedSpendable'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlock>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
