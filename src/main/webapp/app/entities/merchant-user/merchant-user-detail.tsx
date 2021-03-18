import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './merchant-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMerchantUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MerchantUserDetail = (props: IMerchantUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { merchantUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="merchantUserDetailsHeading">
          MerchantUser [<strong>{merchantUserEntity.id}</strong>]
        </h2>
        <dl className="jh-entity-details">
          <dt>User</dt>
          <dd>{merchantUserEntity.user ? merchantUserEntity.user.id : ''}</dd>
          <dt>Merchant</dt>
          <dd>{merchantUserEntity.merchant ? merchantUserEntity.merchant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/merchant-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/merchant-user/${merchantUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ merchantUser }: IRootState) => ({
  merchantUserEntity: merchantUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MerchantUserDetail);
