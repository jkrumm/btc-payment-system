import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConfidence } from 'app/shared/model/confidence.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ConfidenceService } from './confidence.service';
import { ConfidenceDeleteDialogComponent } from './confidence-delete-dialog.component';

@Component({
  selector: 'jhi-confidence',
  templateUrl: './confidence.component.html',
})
export class ConfidenceComponent implements OnInit, OnDestroy {
  confidences: IConfidence[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected confidenceService: ConfidenceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.confidences = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.confidenceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IConfidence[]>) => this.paginateConfidences(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.confidences = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConfidences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConfidence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConfidences(): void {
    this.eventSubscriber = this.eventManager.subscribe('confidenceListModification', () => this.reset());
  }

  delete(confidence: IConfidence): void {
    const modalRef = this.modalService.open(ConfidenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.confidence = confidence;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateConfidences(data: IConfidence[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.confidences.push(data[i]);
      }
    }
  }
}
