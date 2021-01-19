import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fee.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeeDetail = (props: IFeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { feeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feeDetailsHeading">
          Fee [<strong>{feeEntity.id}</strong>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="feeType">Fee Type</span>
            <UncontrolledTooltip target="feeType">Fees can be ZERO, LOW, HIGH</UncontrolledTooltip>
          </dt>
          <dd>{feeEntity.feeType}</dd>
          <dt>
            <span id="percent">Percent</span>
            <UncontrolledTooltip target="percent">Percentage for a small amount transaction</UncontrolledTooltip>
          </dt>
          <dd>{feeEntity.percent}</dd>
          <dt>
            <span id="percentSecure">Percent Secure</span>
            <UncontrolledTooltip target="percentSecure">Percentage for a high amount transaction</UncontrolledTooltip>
          </dt>
          <dd>{feeEntity.percentSecure}</dd>
        </dl>
        <Button tag={Link} to="/fee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fee/${feeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ fee }: IRootState) => ({
  feeEntity: fee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeeDetail);
