import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Transaction from './transaction';
import Confidence from './confidence';
import Merchant from './merchant';
import Fee from './fee';
import MerchantUser from './merchant-user';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}transaction`} component={Transaction} />
      <ErrorBoundaryRoute path={`${match.url}confidence`} component={Confidence} />
      <ErrorBoundaryRoute path={`${match.url}merchant`} component={Merchant} />
      <ErrorBoundaryRoute path={`${match.url}fee`} component={Fee} />
      <ErrorBoundaryRoute path={`${match.url}merchant-user`} component={MerchantUser} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
