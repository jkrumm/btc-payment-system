import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMerchantUser, MerchantUser } from 'app/shared/model/merchant-user.model';
import { MerchantUserService } from './merchant-user.service';
import { MerchantUserComponent } from './merchant-user.component';
import { MerchantUserDetailComponent } from './merchant-user-detail.component';
import { MerchantUserUpdateComponent } from './merchant-user-update.component';

@Injectable({ providedIn: 'root' })
export class MerchantUserResolve implements Resolve<IMerchantUser> {
  constructor(private service: MerchantUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMerchantUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((merchantUser: HttpResponse<MerchantUser>) => {
          if (merchantUser.body) {
            return of(merchantUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MerchantUser());
  }
}

export const merchantUserRoute: Routes = [
  {
    path: '',
    component: MerchantUserComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'btcPaymentSystemApp.merchantUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MerchantUserDetailComponent,
    resolve: {
      merchantUser: MerchantUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchantUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MerchantUserUpdateComponent,
    resolve: {
      merchantUser: MerchantUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchantUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MerchantUserUpdateComponent,
    resolve: {
      merchantUser: MerchantUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.merchantUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
