import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/transaction">
      Transaction
    </MenuItem>
    <MenuItem icon="asterisk" to="/confidence">
      Confidence
    </MenuItem>
    <MenuItem icon="asterisk" to="/merchant">
      Merchant
    </MenuItem>
    <MenuItem icon="asterisk" to="/fee">
      Fee
    </MenuItem>
    <MenuItem icon="asterisk" to="/merchant-user">
      Merchant User
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
