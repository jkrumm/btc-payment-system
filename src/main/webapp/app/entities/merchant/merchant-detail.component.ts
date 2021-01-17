import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMerchant } from 'app/shared/model/merchant.model';

@Component({
  selector: 'jhi-merchant-detail',
  templateUrl: './merchant-detail.component.html',
})
export class MerchantDetailComponent implements OnInit {
  merchant: IMerchant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchant }) => (this.merchant = merchant));
  }

  previousState(): void {
    window.history.back();
  }
}
