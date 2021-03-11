import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { getWallet, getTransactions } from 'app/btc/user.reducer';
import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert, Collapse } from 'antd';
import dayjs from 'dayjs';

import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import { DateDiff } from 'app/btc/datediff';

('../../datediff');

import './wallet.scss';

export interface ITransactionProps extends StateProps, DispatchProps {
  dateDiff: any;
}

const Wallet = (props: ITransactionProps) => {
  const { wallet, tx, transactions } = props;
  const [timeAgo, setTimeAgo] = useState(new DateDiff(new Date(), new Date(wallet.blockMinedAt)));

  useEffect(() => {
    props.getTransactions();
    props.getWallet();
    const interval = setInterval(() => {
      const date = new Date();
      const dateDiff = new DateDiff(date.getTime(), new Date(wallet.blockMinedAt));
      setTimeAgo(dateDiff);
    }, 1000);
    return () => clearInterval(interval);
  }, [tx, wallet.blockHeight]);

  return (
    <div>
      <Heading icon={faHandshake} heading="Wallet" />
      {props.loading && <h1>LOADING</h1>}
      {props.errorMessage && <Alert message="Error Message" description={props.errorMessage} type="warning" closable />}
      <WhiteSpace size={'xl'} />
      <Card title="Letzter Block">
        <Statistic title="Block Höhe" value={wallet.blockHeight} />
        <WhiteSpace size={'lg'} />
        <Statistic title="Zeit seit letztem Block" value={timeAgo.minutes + ' Minuten ' + timeAgo.seconds + ' Sekunden'} />
      </Card>
      <WhiteSpace size={'md'} />
      <Collapse>
        <Collapse.Panel header="Weitere Details" key={1}>
          {props.isAuthenticated && 'Authenticated'}
          <br />
          Mined: {wallet.blockMinedAt}
          <WhiteSpace size={'md'} />
          <Statistic title="Available" value={wallet.available} />
          <Statistic title="Available spendable" value={wallet.availableSpendable} />
          <Statistic title="Estimated" value={wallet.estimated} />
          <Statistic title="Estimated Spendable" value={wallet.estimatedSpendable} />
          <Statistic title="Tx Pending" value={wallet.pending} />
          <Statistic title="Tx Unspent" value={wallet.unspent} />
          <Statistic title="Tx Spent" value={wallet.spent} />
          <Statistic title="Tx Dead" value={wallet.dead} />
        </Collapse.Panel>
      </Collapse>
      <WhiteSpace size={'md'} />
      <Card title="Transaktionen" className="no-padding">
        <Collapse>
          {transactions[1] !== undefined && transactions[1] !== null ? (
            <>
              {transactions.map(item => (
                <>
                  {item.actualAmount != null && (
                    <Collapse.Panel
                      key={item.id}
                      className={item.confidenceType === 'CONFIRMED' ? 'success' : 'warning'}
                      header={
                        <div>
                          <span>
                            {item.amount}€ | {item.confirmations} | {item.transactionType}
                          </span>
                          <span>{item.timeAgo}</span>
                        </div>
                      }
                    >
                      <Statistic title="Addresse" value={item.address} className="tiny" />
                      <Statistic title="Transaktion Hash" value={item.txHash} className="tiny" />
                      <Statistic
                        title="Transaktion initiiert"
                        value={dayjs(item.initiatedAt).format('DD.MM.YY HH:mm:ss')}
                        className="tiny"
                      />
                      <Statistic title="Validierung" value={item.confidenceType} className="tiny" />
                      <Statistic title="Bestätigungen" value={item.confirmations} className="tiny" />
                      <WhiteSpace size={'md'} />
                      <Statistic title="Preis" value={item.amount} suffix={' €'} precision={2} className="tiny" />
                      <Statistic title="BTC / Euro" value={item.btcUsd} suffix={' €'} precision={2} className="tiny" />
                      <Statistic
                        title="Erwartete BTC"
                        value={item.expectedAmount / 100000000}
                        suffix={' BTC'}
                        precision={8}
                        className="tiny"
                      />
                      <Statistic
                        title="Erhaltene BTC"
                        value={item.actualAmount / 100000000}
                        suffix={' BTC'}
                        precision={8}
                        className="tiny"
                      />
                      <Statistic
                        title="Transaktionskosten"
                        value={item.transactionFee / 100000000}
                        suffix={' BTC'}
                        precision={8}
                        className="tiny"
                      />
                      <Statistic title="Servicekosten" value={item.serviceFee / 100000000} suffix={' BTC'} precision={8} className="tiny" />
                    </Collapse.Panel>
                  )}
                </>
              ))}
            </>
          ) : (
            <h3>Loading!</h3>
          )}
        </Collapse>
      </Card>
    </div>
  );
};

const mapStateToProps = ({ authentication, user }: IRootState) => ({
  isAuthenticated: authentication.isAuthenticated,
  wallet: user.wallet,
  tx: user.tx,
  transactions: user.transactions,
  loading: user.loading,
  errorMessage: user.errorMessage,
});

const mapDispatchToProps = { getWallet, getTransactions };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallet);
