import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './merchant.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMerchantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MerchantDetail = (props: IMerchantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { merchantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="merchantDetailsHeading">
          Merchant [<strong>{merchantEntity.id}</strong>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
            <UncontrolledTooltip target="name">Merchant name</UncontrolledTooltip>
          </dt>
          <dd>{merchantEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{merchantEntity.email}</dd>
          <dt>
            <span id="forward">Forward</span>
          </dt>
          <dd>{merchantEntity.forward}</dd>
          <dt>Fee</dt>
          <dd>{merchantEntity.fee ? merchantEntity.fee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/merchant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/merchant/${merchantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ merchant }: IRootState) => ({
  merchantEntity: merchant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MerchantDetail);
