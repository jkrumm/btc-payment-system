import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Fee from './fee';
import FeeDetail from './fee-detail';
import FeeUpdate from './fee-update';
import FeeDeleteDialog from './fee-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Fee} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeeDeleteDialog} />
  </>
);

export default Routes;
