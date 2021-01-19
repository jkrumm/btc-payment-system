import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITransaction } from 'app/shared/model/transaction.model';
import { getEntities as getTransactions } from 'app/entities/transaction/transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './confidence.reducer';
import { IConfidence } from 'app/shared/model/confidence.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConfidenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConfidenceUpdate = (props: IConfidenceUpdateProps) => {
  const [transactionId, setTransactionId] = useState('0');
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { confidenceEntity, transactions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/confidence' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTransactions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.changeAt = convertDateTimeToServer(values.changeAt);

    if (errors.length === 0) {
      const entity = {
        ...confidenceEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="btcPaymentSystemApp.confidence.home.createOrEditLabel" data-cy="ConfidenceCreateUpdateHeading">
            Create or edit a Confidence
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : confidenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="confidence-id">ID</Label>
                  <AvInput id="confidence-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="changeAtLabel" for="confidence-changeAt">
                  Change At
                </Label>
                <AvInput
                  id="confidence-changeAt"
                  data-cy="changeAt"
                  type="datetime-local"
                  className="form-control"
                  name="changeAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.confidenceEntity.changeAt)}
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="confidenceTypeLabel" for="confidence-confidenceType">
                  Confidence Type
                </Label>
                <AvInput
                  id="confidence-confidenceType"
                  data-cy="confidenceType"
                  type="select"
                  className="form-control"
                  name="confidenceType"
                  value={(!isNew && confidenceEntity.confidenceType) || 'INCOMING'}
                >
                  <option value="INCOMING">INCOMING</option>
                  <option value="BUILDING">BUILDING</option>
                  <option value="DEAD">DEAD</option>
                  <option value="UNKNOWN">UNKNOWN</option>
                  <option value="CONFIRMED">CONFIRMED</option>
                </AvInput>
                <UncontrolledTooltip target="confidenceTypeLabel">Current state of a transaction</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="confirmationsLabel" for="confidence-confirmations">
                  Confirmations
                </Label>
                <AvField
                  id="confidence-confirmations"
                  data-cy="confirmations"
                  type="string"
                  className="form-control"
                  name="confirmations"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    min: { value: 0, errorMessage: 'This field should be at least 0.' },
                    max: { value: 6, errorMessage: 'This field cannot be more than 6.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
                <UncontrolledTooltip target="confirmationsLabel">Amount of confirmations through new blocks</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="confidence-transaction">Transaction</Label>
                <AvInput id="confidence-transaction" data-cy="transaction" type="select" className="form-control" name="transaction.id">
                  <option value="" key="0" />
                  {transactions
                    ? transactions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/confidence" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  transactions: storeState.transaction.entities,
  confidenceEntity: storeState.confidence.entity,
  loading: storeState.confidence.loading,
  updating: storeState.confidence.updating,
  updateSuccess: storeState.confidence.updateSuccess,
});

const mapDispatchToProps = {
  getTransactions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConfidenceUpdate);
