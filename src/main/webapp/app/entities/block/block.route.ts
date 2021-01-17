import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBlock, Block } from 'app/shared/model/block.model';
import { BlockService } from './block.service';
import { BlockComponent } from './block.component';
import { BlockDetailComponent } from './block-detail.component';
import { BlockUpdateComponent } from './block-update.component';

@Injectable({ providedIn: 'root' })
export class BlockResolve implements Resolve<IBlock> {
  constructor(private service: BlockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBlock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((block: HttpResponse<Block>) => {
          if (block.body) {
            return of(block.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Block());
  }
}

export const blockRoute: Routes = [
  {
    path: '',
    component: BlockComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.block.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BlockDetailComponent,
    resolve: {
      block: BlockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.block.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BlockUpdateComponent,
    resolve: {
      block: BlockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.block.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BlockUpdateComponent,
    resolve: {
      block: BlockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.block.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
