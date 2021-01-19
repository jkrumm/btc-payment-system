import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Confidence from './confidence';
import ConfidenceDetail from './confidence-detail';
import ConfidenceUpdate from './confidence-update';
import ConfidenceDeleteDialog from './confidence-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConfidenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConfidenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConfidenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Confidence} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConfidenceDeleteDialog} />
  </>
);

export default Routes;
