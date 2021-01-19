import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMerchant } from 'app/shared/model/merchant.model';
import { getEntities as getMerchants } from 'app/entities/merchant/merchant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TransactionUpdate = (props: ITransactionUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [merchantId, setMerchantId] = useState('0');
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { transactionEntity, users, merchants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/transaction' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getMerchants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.initiatedAt = convertDateTimeToServer(values.initiatedAt);

    if (errors.length === 0) {
      const entity = {
        ...transactionEntity,
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
          <h2 id="btcPaymentSystemApp.transaction.home.createOrEditLabel" data-cy="TransactionCreateUpdateHeading">
            Create or edit a Transaction
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : transactionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="transaction-id">ID</Label>
                  <AvInput id="transaction-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="initiatedAtLabel" for="transaction-initiatedAt">
                  Initiated At
                </Label>
                <AvInput
                  id="transaction-initiatedAt"
                  data-cy="initiatedAt"
                  type="datetime-local"
                  className="form-control"
                  name="initiatedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.transactionEntity.initiatedAt)}
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
                <UncontrolledTooltip target="initiatedAtLabel">Transaction initiated at</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="transactionTypeLabel" for="transaction-transactionType">
                  Transaction Type
                </Label>
                <AvInput
                  id="transaction-transactionType"
                  data-cy="transactionType"
                  type="select"
                  className="form-control"
                  name="transactionType"
                  value={(!isNew && transactionEntity.transactionType) || 'INCOMING_UNKNOWN'}
                >
                  <option value="INCOMING_UNKNOWN">INCOMING_UNKNOWN</option>
                  <option value="INCOMING_CUSTOMER">INCOMING_CUSTOMER</option>
                  <option value="FORWARD_MERCHANT">FORWARD_MERCHANT</option>
                  <option value="FORWARD_HOLDINGS">FORWARD_HOLDINGS</option>
                </AvInput>
                <UncontrolledTooltip target="transactionTypeLabel">Transaction enum type</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="txHashLabel" for="transaction-txHash">
                  Tx Hash
                </Label>
                <AvField id="transaction-txHash" data-cy="txHash" type="text" name="txHash" validate={{}} />
                <UncontrolledTooltip target="txHashLabel">Transaction hash</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="expectedAmountLabel" for="transaction-expectedAmount">
                  Expected Amount
                </Label>
                <AvField
                  id="transaction-expectedAmount"
                  data-cy="expectedAmount"
                  type="string"
                  className="form-control"
                  name="expectedAmount"
                />
                <UncontrolledTooltip target="expectedAmountLabel">Expected BTC amount from the customer</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="actualAmountLabel" for="transaction-actualAmount">
                  Actual Amount
                </Label>
                <AvField id="transaction-actualAmount" data-cy="actualAmount" type="string" className="form-control" name="actualAmount" />
                <UncontrolledTooltip target="actualAmountLabel">Actual BTC amount of the transaction</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="transactionFeeLabel" for="transaction-transactionFee">
                  Transaction Fee
                </Label>
                <AvField
                  id="transaction-transactionFee"
                  data-cy="transactionFee"
                  type="string"
                  className="form-control"
                  name="transactionFee"
                />
                <UncontrolledTooltip target="transactionFeeLabel">BTC transaction fee</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="serviceFeeLabel" for="transaction-serviceFee">
                  Service Fee
                </Label>
                <AvField id="transaction-serviceFee" data-cy="serviceFee" type="string" className="form-control" name="serviceFee" />
                <UncontrolledTooltip target="serviceFeeLabel">Service fee</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="btcUsdLabel" for="transaction-btcUsd">
                  Btc Usd
                </Label>
                <AvField id="transaction-btcUsd" data-cy="btcUsd" type="string" className="form-control" name="btcUsd" />
                <UncontrolledTooltip target="btcUsdLabel">BTC price at intiation</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="transaction-user">User</Label>
                <AvInput id="transaction-user" data-cy="user" type="select" className="form-control" name="user.id">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="transaction-merchant">Merchant</Label>
                <AvInput id="transaction-merchant" data-cy="merchant" type="select" className="form-control" name="merchant.id">
                  <option value="" key="0" />
                  {merchants
                    ? merchants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/transaction" replace color="info">
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
  users: storeState.userManagement.users,
  merchants: storeState.merchant.entities,
  transactionEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating,
  updateSuccess: storeState.transaction.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getMerchants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TransactionUpdate);
