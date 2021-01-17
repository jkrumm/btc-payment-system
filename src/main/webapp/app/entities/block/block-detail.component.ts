import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBlock } from 'app/shared/model/block.model';

@Component({
  selector: 'jhi-block-detail',
  templateUrl: './block-detail.component.html',
})
export class BlockDetailComponent implements OnInit {
  block: IBlock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ block }) => (this.block = block));
  }

  previousState(): void {
    window.history.back();
  }
}
