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

function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
}

const Wallet = (props: ITransactionProps) => {
  const { wallet, tx, transactions } = props;
  const [txData, setTxData] = useState(transactions);
  const [trigger, setTrigger] = useState(false);
  const [timeAgo, setTimeAgo] = useState(new DateDiff(new Date(), new Date(wallet.blockMinedAt)));

  useEffect(() => {
    props.getTransactions();
    props.getWallet();
    const interval = setInterval(() => {
      const date = new Date();
      const dateDiff = new DateDiff(date.getTime(), new Date(wallet.blockMinedAt));
      setTimeAgo(dateDiff);
      const newTxData = txData;
      newTxData.forEach(txDataItem => {
        const df = new DateDiff(new Date(), new Date(txDataItem.initiatedAt));
        txDataItem.dateDiff = {
          days: 0,
          hours: 0,
          minutes: 0,
          seconds: 0,
        };
        txDataItem.dateDiff.days = df.days;
        txDataItem.dateDiff.hours = df.hours;
        txDataItem.dateDiff.minutes = df.minutes;
        txDataItem.dateDiff.seconds = df.seconds;
      });
      newTxData.sort((a, b) => {
        const date1 = new Date(a.initiatedAt);
        const date2 = new Date(b.initiatedAt);
        return date2.getTime() - date1.getTime();
      });
      if (tx.length > 1) {
        tx.forEach(txItem => {
          newTxData.forEach(txDataItem => {
            if (txItem.address === txDataItem.address) {
              txDataItem.confidence = txItem.confidence;
            }
          });
        });
        setTxData(newTxData);
      }
      setTrigger(!trigger);
    }, 15000);
    return () => clearInterval(interval);
  }, [trigger]);

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
          {txData[1].dateDiff != null ? (
            <>
              {txData.map((item, index) => (
                <div key={index} className="transaction-list">
                  <span>{item.transactionType}</span>
                  <div>
                    <div>
                      <span>
                        {item.dateDiff.days}d {item.dateDiff.hours}h {item.dateDiff.minutes}m {item.dateDiff.seconds}s
                      </span>
                    </div>
                    {item.confidence != null ? (
                      <div>
                        Bestätigungen <span>{item.confidence.confirmations}</span>
                      </div>
                    ) : (
                      <div>
                        Bestätigungen <span>Nan</span>
                      </div>
                    )}
                  </div>
                  <div>
                    <div>
                      Amount <span>{item.amount}</span>
                    </div>
                    <div>
                      BTC <span>{item.actualAmount}</span>
                    </div>
                  </div>
                </div>
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
