import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TransactionDetail = (props: ITransactionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { transactionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDetailsHeading">
          Transaction [<strong>{transactionEntity.id}</strong>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="initiatedAt">Initiated At</span>
            <UncontrolledTooltip target="initiatedAt">Transaction initiated at</UncontrolledTooltip>
          </dt>
          <dd>
            {transactionEntity.initiatedAt ? (
              <TextFormat value={transactionEntity.initiatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="transactionType">Transaction Type</span>
            <UncontrolledTooltip target="transactionType">Transaction enum type</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.transactionType}</dd>
          <dt>
            <span id="txHash">Tx Hash</span>
            <UncontrolledTooltip target="txHash">Transaction hash</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.txHash}</dd>
          <dt>
            <span id="expectedAmount">Expected Amount</span>
            <UncontrolledTooltip target="expectedAmount">Expected BTC amount from the customer</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.expectedAmount}</dd>
          <dt>
            <span id="actualAmount">Actual Amount</span>
            <UncontrolledTooltip target="actualAmount">Actual BTC amount of the transaction</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.actualAmount}</dd>
          <dt>
            <span id="transactionFee">Transaction Fee</span>
            <UncontrolledTooltip target="transactionFee">BTC transaction fee</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.transactionFee}</dd>
          <dt>
            <span id="serviceFee">Service Fee</span>
            <UncontrolledTooltip target="serviceFee">Service fee</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.serviceFee}</dd>
          <dt>
            <span id="btcEuro">Btc Euro</span>
            <UncontrolledTooltip target="btcEuro">BTC/Euro price at intiation</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.btcEuro}</dd>
          <dt>
            <span id="address">Address</span>
            <UncontrolledTooltip target="address">Transaction address</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.address}</dd>
          <dt>
            <span id="amount">Amount</span>
            <UncontrolledTooltip target="amount">Euro price</UncontrolledTooltip>
          </dt>
          <dd>{transactionEntity.amount}</dd>
          <dt>User</dt>
          <dd>{transactionEntity.user ? transactionEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ transaction }: IRootState) => ({
  transactionEntity: transaction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TransactionDetail);
