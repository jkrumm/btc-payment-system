import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MerchantUser from './merchant-user';
import MerchantUserDetail from './merchant-user-detail';
import MerchantUserUpdate from './merchant-user-update';
import MerchantUserDeleteDialog from './merchant-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MerchantUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MerchantUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MerchantUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={MerchantUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MerchantUserDeleteDialog} />
  </>
);

export default Routes;
