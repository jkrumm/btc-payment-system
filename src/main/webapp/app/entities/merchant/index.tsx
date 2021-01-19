import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Merchant from './merchant';
import MerchantDetail from './merchant-detail';
import MerchantUpdate from './merchant-update';
import MerchantDeleteDialog from './merchant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MerchantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MerchantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MerchantDetail} />
      <ErrorBoundaryRoute path={match.url} component={Merchant} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MerchantDeleteDialog} />
  </>
);

export default Routes;
