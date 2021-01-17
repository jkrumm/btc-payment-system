import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConfidence, Confidence } from 'app/shared/model/confidence.model';
import { ConfidenceService } from './confidence.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction/transaction.service';

@Component({
  selector: 'jhi-confidence-update',
  templateUrl: './confidence-update.component.html',
})
export class ConfidenceUpdateComponent implements OnInit {
  isSaving = false;
  transactions: ITransaction[] = [];

  editForm = this.fb.group({
    id: [],
    confidenceType: [],
    confirmations: [],
    transactionId: [],
  });

  constructor(
    protected confidenceService: ConfidenceService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ confidence }) => {
      this.updateForm(confidence);

      this.transactionService.query().subscribe((res: HttpResponse<ITransaction[]>) => (this.transactions = res.body || []));
    });
  }

  updateForm(confidence: IConfidence): void {
    this.editForm.patchValue({
      id: confidence.id,
      confidenceType: confidence.confidenceType,
      confirmations: confidence.confirmations,
      transactionId: confidence.transactionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const confidence = this.createFromForm();
    if (confidence.id !== undefined) {
      this.subscribeToSaveResponse(this.confidenceService.update(confidence));
    } else {
      this.subscribeToSaveResponse(this.confidenceService.create(confidence));
    }
  }

  private createFromForm(): IConfidence {
    return {
      ...new Confidence(),
      id: this.editForm.get(['id'])!.value,
      confidenceType: this.editForm.get(['confidenceType'])!.value,
      confirmations: this.editForm.get(['confirmations'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfidence>>): void {
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

  trackById(index: number, item: ITransaction): any {
    return item.id;
  }
}
