import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IBlock } from 'app/shared/model/block.model';
import { BlockService } from 'app/entities/block/block.service';

type SelectableEntity = IUser | IBlock;

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  blocks: IBlock[] = [];

  editForm = this.fb.group({
    id: [],
    initiatedAt: [null, [Validators.required]],
    transactionType: [null, [Validators.required]],
    isMempool: [null, [Validators.required]],
    txHash: [null, []],
    fromAddress: [],
    toAddress: [null, [Validators.required]],
    expectedAmount: [null, [Validators.required]],
    amount: [],
    serviceFee: [null, [Validators.required]],
    btcPrice: [null, [Validators.required]],
    isWithdrawed: [null, [Validators.required]],
    userId: [],
    blockId: [],
  });

  constructor(
    protected transactionService: TransactionService,
    protected userService: UserService,
    protected blockService: BlockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      if (!transaction.id) {
        const today = moment().startOf('day');
        transaction.initiatedAt = today;
      }

      this.updateForm(transaction);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.blockService.query().subscribe((res: HttpResponse<IBlock[]>) => (this.blocks = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      initiatedAt: transaction.initiatedAt ? transaction.initiatedAt.format(DATE_TIME_FORMAT) : null,
      transactionType: transaction.transactionType,
      isMempool: transaction.isMempool,
      txHash: transaction.txHash,
      fromAddress: transaction.fromAddress,
      toAddress: transaction.toAddress,
      expectedAmount: transaction.expectedAmount,
      amount: transaction.amount,
      serviceFee: transaction.serviceFee,
      btcPrice: transaction.btcPrice,
      isWithdrawed: transaction.isWithdrawed,
      userId: transaction.userId,
      blockId: transaction.blockId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      initiatedAt: this.editForm.get(['initiatedAt'])!.value
        ? moment(this.editForm.get(['initiatedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      transactionType: this.editForm.get(['transactionType'])!.value,
      isMempool: this.editForm.get(['isMempool'])!.value,
      txHash: this.editForm.get(['txHash'])!.value,
      fromAddress: this.editForm.get(['fromAddress'])!.value,
      toAddress: this.editForm.get(['toAddress'])!.value,
      expectedAmount: this.editForm.get(['expectedAmount'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      serviceFee: this.editForm.get(['serviceFee'])!.value,
      btcPrice: this.editForm.get(['btcPrice'])!.value,
      isWithdrawed: this.editForm.get(['isWithdrawed'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      blockId: this.editForm.get(['blockId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
