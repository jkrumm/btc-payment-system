import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConfidence, Confidence } from 'app/shared/model/confidence.model';
import { ConfidenceService } from './confidence.service';
import { ConfidenceComponent } from './confidence.component';
import { ConfidenceDetailComponent } from './confidence-detail.component';
import { ConfidenceUpdateComponent } from './confidence-update.component';

@Injectable({ providedIn: 'root' })
export class ConfidenceResolve implements Resolve<IConfidence> {
  constructor(private service: ConfidenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConfidence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((confidence: HttpResponse<Confidence>) => {
          if (confidence.body) {
            return of(confidence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Confidence());
  }
}

export const confidenceRoute: Routes = [
  {
    path: '',
    component: ConfidenceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.confidence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConfidenceDetailComponent,
    resolve: {
      confidence: ConfidenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.confidence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConfidenceUpdateComponent,
    resolve: {
      confidence: ConfidenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.confidence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConfidenceUpdateComponent,
    resolve: {
      confidence: ConfidenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'btcPaymentSystemApp.confidence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
