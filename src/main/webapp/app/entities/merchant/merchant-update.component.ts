import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMerchant, Merchant } from 'app/shared/model/merchant.model';
import { MerchantService } from './merchant.service';
import { IFee } from 'app/shared/model/fee.model';
import { FeeService } from 'app/entities/fee/fee.service';

@Component({
  selector: 'jhi-merchant-update',
  templateUrl: './merchant-update.component.html',
})
export class MerchantUpdateComponent implements OnInit {
  isSaving = false;
  fees: IFee[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    feeId: [],
  });

  constructor(
    protected merchantService: MerchantService,
    protected feeService: FeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchant }) => {
      this.updateForm(merchant);

      this.feeService
        .query({ filter: 'merchant-is-null' })
        .pipe(
          map((res: HttpResponse<IFee[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFee[]) => {
          if (!merchant.feeId) {
            this.fees = resBody;
          } else {
            this.feeService
              .find(merchant.feeId)
              .pipe(
                map((subRes: HttpResponse<IFee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFee[]) => (this.fees = concatRes));
          }
        });
    });
  }

  updateForm(merchant: IMerchant): void {
    this.editForm.patchValue({
      id: merchant.id,
      name: merchant.name,
      email: merchant.email,
      feeId: merchant.feeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const merchant = this.createFromForm();
    if (merchant.id !== undefined) {
      this.subscribeToSaveResponse(this.merchantService.update(merchant));
    } else {
      this.subscribeToSaveResponse(this.merchantService.create(merchant));
    }
  }

  private createFromForm(): IMerchant {
    return {
      ...new Merchant(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      feeId: this.editForm.get(['feeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMerchant>>): void {
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

  trackById(index: number, item: IFee): any {
    return item.id;
  }
}
