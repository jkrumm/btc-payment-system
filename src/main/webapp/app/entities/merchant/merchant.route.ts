import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMerchant, Merchant } from 'app/shared/model/merchant.model';
import { MerchantService } from './merchant.service';
import { MerchantComponent } from './merchant.component';
import { MerchantDetailComponent } from './merchant-detail.component';
import { MerchantUpdateComponent } from './merchant-update.component';

@Injectable({ providedIn: 'root' })
export class MerchantResolve implements Resolve<IMerchant> {
  constructor(private service: MerchantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMerchant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((merchant: HttpResponse<Merchant>) => {
          if (merchant.body) {
            return of(merchant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Merchant());
  }
}

export const merchantRoute: Routes = [
  {
    path: '',
    component: MerchantComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'btcPaymentSystemApp.merchant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MerchantDetailComponent,
    resolve: {
      merchant: MerchantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MerchantUpdateComponent,
    resolve: {
      merchant: MerchantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MerchantUpdateComponent,
    resolve: {
      merchant: MerchantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
