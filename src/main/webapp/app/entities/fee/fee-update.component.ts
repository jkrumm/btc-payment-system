import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFee, Fee } from 'app/shared/model/fee.model';
import { FeeService } from './fee.service';

@Component({
  selector: 'jhi-fee-update',
  templateUrl: './fee-update.component.html',
})
export class FeeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    feeType: [null, [Validators.required]],
    percent: [null, [Validators.required]],
    percentSecure: [null, [Validators.required]],
  });

  constructor(protected feeService: FeeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fee }) => {
      this.updateForm(fee);
    });
  }

  updateForm(fee: IFee): void {
    this.editForm.patchValue({
      id: fee.id,
      feeType: fee.feeType,
      percent: fee.percent,
      percentSecure: fee.percentSecure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fee = this.createFromForm();
    if (fee.id !== undefined) {
      this.subscribeToSaveResponse(this.feeService.update(fee));
    } else {
      this.subscribeToSaveResponse(this.feeService.create(fee));
    }
  }

  private createFromForm(): IFee {
    return {
      ...new Fee(),
      id: this.editForm.get(['id'])!.value,
      feeType: this.editForm.get(['feeType'])!.value,
      percent: this.editForm.get(['percent'])!.value,
      percentSecure: this.editForm.get(['percentSecure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFee>>): void {
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
