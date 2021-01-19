import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './confidence.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConfidenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConfidenceDetail = (props: IConfidenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { confidenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="confidenceDetailsHeading">
          Confidence [<strong>{confidenceEntity.id}</strong>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="changeAt">Change At</span>
          </dt>
          <dd>
            {confidenceEntity.changeAt ? <TextFormat value={confidenceEntity.changeAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="confidenceType">Confidence Type</span>
            <UncontrolledTooltip target="confidenceType">Current state of a transaction</UncontrolledTooltip>
          </dt>
          <dd>{confidenceEntity.confidenceType}</dd>
          <dt>
            <span id="confirmations">Confirmations</span>
            <UncontrolledTooltip target="confirmations">Amount of confirmations through new blocks</UncontrolledTooltip>
          </dt>
          <dd>{confidenceEntity.confirmations}</dd>
          <dt>Transaction</dt>
          <dd>{confidenceEntity.transaction ? confidenceEntity.transaction.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/confidence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/confidence/${confidenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ confidence }: IRootState) => ({
  confidenceEntity: confidence.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConfidenceDetail);
