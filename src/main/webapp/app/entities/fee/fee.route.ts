import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFee, Fee } from 'app/shared/model/fee.model';
import { FeeService } from './fee.service';
import { FeeComponent } from './fee.component';
import { FeeDetailComponent } from './fee-detail.component';
import { FeeUpdateComponent } from './fee-update.component';

@Injectable({ providedIn: 'root' })
export class FeeResolve implements Resolve<IFee> {
  constructor(private service: FeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fee: HttpResponse<Fee>) => {
          if (fee.body) {
            return of(fee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fee());
  }
}

export const feeRoute: Routes = [
  {
    path: '',
    component: FeeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'btcPaymentSystemApp.fee.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeeDetailComponent,
    resolve: {
      fee: FeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.fee.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeeUpdateComponent,
    resolve: {
      fee: FeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.fee.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeeUpdateComponent,
    resolve: {
      fee: FeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.fee.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
