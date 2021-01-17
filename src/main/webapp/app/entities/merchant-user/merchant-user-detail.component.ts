import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMerchantUser } from 'app/shared/model/merchant-user.model';

@Component({
  selector: 'jhi-merchant-user-detail',
  templateUrl: './merchant-user-detail.component.html',
})
export class MerchantUserDetailComponent implements OnInit {
  merchantUser: IMerchantUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchantUser }) => (this.merchantUser = merchantUser));
  }

  previousState(): void {
    window.history.back();
  }
}
