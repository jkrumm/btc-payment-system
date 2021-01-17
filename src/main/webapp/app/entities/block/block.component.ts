import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBlock } from 'app/shared/model/block.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BlockService } from './block.service';
import { BlockDeleteDialogComponent } from './block-delete-dialog.component';

@Component({
  selector: 'jhi-block',
  templateUrl: './block.component.html',
})
export class BlockComponent implements OnInit, OnDestroy {
  blocks: IBlock[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected blockService: BlockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.blocks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.blockService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IBlock[]>) => this.paginateBlocks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.blocks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBlocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBlock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBlocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('blockListModification', () => this.reset());
  }

  delete(block: IBlock): void {
    const modalRef = this.modalService.open(BlockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.block = block;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBlocks(data: IBlock[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.blocks.push(data[i]);
      }
    }
  }
}
