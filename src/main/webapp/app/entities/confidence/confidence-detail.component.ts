import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfidence } from 'app/shared/model/confidence.model';

@Component({
  selector: 'jhi-confidence-detail',
  templateUrl: './confidence-detail.component.html',
})
export class ConfidenceDetailComponent implements OnInit {
  confidence: IConfidence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ confidence }) => (this.confidence = confidence));
  }

  previousState(): void {
    window.history.back();
  }
}
