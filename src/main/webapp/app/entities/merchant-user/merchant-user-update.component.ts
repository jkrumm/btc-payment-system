import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMerchantUser, MerchantUser } from 'app/shared/model/merchant-user.model';
import { MerchantUserService } from './merchant-user.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMerchant } from 'app/shared/model/merchant.model';
import { MerchantService } from 'app/entities/merchant/merchant.service';

type SelectableEntity = IUser | IMerchant;

@Component({
  selector: 'jhi-merchant-user-update',
  templateUrl: './merchant-user-update.component.html',
})
export class MerchantUserUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  merchants: IMerchant[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [],
    merchantId: [],
  });

  constructor(
    protected merchantUserService: MerchantUserService,
    protected userService: UserService,
    protected merchantService: MerchantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchantUser }) => {
      this.updateForm(merchantUser);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.merchantService.query().subscribe((res: HttpResponse<IMerchant[]>) => (this.merchants = res.body || []));
    });
  }

  updateForm(merchantUser: IMerchantUser): void {
    this.editForm.patchValue({
      id: merchantUser.id,
      userId: merchantUser.userId,
      merchantId: merchantUser.merchantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const merchantUser = this.createFromForm();
    if (merchantUser.id !== undefined) {
      this.subscribeToSaveResponse(this.merchantUserService.update(merchantUser));
    } else {
      this.subscribeToSaveResponse(this.merchantUserService.create(merchantUser));
    }
  }

  private createFromForm(): IMerchantUser {
    return {
      ...new MerchantUser(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      merchantId: this.editForm.get(['merchantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMerchantUser>>): void {
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
