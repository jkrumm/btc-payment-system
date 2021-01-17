import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFee } from 'app/shared/model/fee.model';

@Component({
  selector: 'jhi-fee-detail',
  templateUrl: './fee-detail.component.html',
})
export class FeeDetailComponent implements OnInit {
  fee: IFee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fee }) => (this.fee = fee));
  }

  previousState(): void {
    window.history.back();
  }
}
