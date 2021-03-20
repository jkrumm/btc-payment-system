import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { getWallet, getTransactions, getMerchantWallet } from 'app/btc/user.reducer';
import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert, Collapse, Button } from 'antd';
import dayjs from 'dayjs';

import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import { DateDiff } from 'app/btc/datediff';

('../../datediff');

import './wallet.scss';

export interface ITransactionProps extends StateProps, DispatchProps {}

const Wallet = (props: ITransactionProps) => {
  const { wallet, merchantWallet, tx, transactions } = props;
  const [refresh, setRefresh] = useState(0);
  const [timeAgo, setTimeAgo] = useState(new DateDiff(new Date(), new Date(wallet.blockMinedAt)));

  useEffect(() => {
    props.getTransactions();
    props.getWallet();
    props.getMerchantWallet();
    const interval = setInterval(() => {
      const date = new Date();
      const dateDiff = new DateDiff(date.getTime(), new Date(wallet.blockMinedAt));
      setTimeAgo(dateDiff);
    }, 1000);
    return () => clearInterval(interval);
  }, [tx, refresh, wallet.blockHeight]);

  const getTxHistoryClass = item => {
    if (item.transactionType === 'FORWARD') {
      return 'primary';
    } else if ((item.transactionType === 'INCOMING_FAST' || item.confirmations > 0) && item.confidenceType === 'CONFIRMED') {
      return 'success';
    } else if (item.confidenceType === 'CONFIRMED') {
      return 'warning';
    } else if (item.confidenceType === 'BUILDING') {
      return 'warning';
    } else {
      return 'danger';
    }
  };

  return (
    <div>
      <Heading icon={'https://ik.imagekit.io/jtrj8won4m0/BtcPaymentSystem/wallet_lLs8p3Te3QEx.svg'} heading="Wallet" />
      {props.loading && <h1>LOADING</h1>}
      {props.errorMessage && <Alert message="Error Message" description={props.errorMessage} type="warning" closable />}
      <WhiteSpace size={'xl'} />
      <Button onClick={() => setRefresh(refresh + 1)} style={{ width: '100%' }}>
        Aktualisieren
      </Button>
      <WhiteSpace size={'md'} />
      <Card title="Letzter Block">
        <Statistic title="Block Höhe" value={wallet.blockHeight} className="small" />
        <WhiteSpace size={'xs'} />
        <Statistic
          title="Zeit seit letztem Block"
          className="small"
          value={timeAgo.minutes + ' Minuten ' + timeAgo.seconds + ' Sekunden'}
        />
      </Card>
      <Collapse style={{ display: 'none' }}>
        <Collapse.Panel header="Weitere Details" key={1}>
          {props.isAuthenticated && 'Authenticated'}
          <br />
          Mined: {wallet.blockMinedAt}
          <WhiteSpace size={'md'} />
          <Statistic title="Available" value={wallet.available / 100000000} suffix={' BTC'} precision={8} className="tiny" />
          <Statistic
            title="Available spendable"
            value={wallet.availableSpendable / 100000000}
            suffix={' BTC'}
            precision={8}
            className="tiny"
          />
          <Statistic title="Estimated" value={wallet.estimated / 100000000} suffix={' BTC'} precision={8} className="tiny" />
          <Statistic
            title="Estimated Spendable"
            value={wallet.estimatedSpendable / 100000000}
            suffix={' BTC'}
            precision={8}
            className="tiny"
          />
          <Statistic title="Tx Pending" value={wallet.pending} className="tiny" />
          <Statistic title="Tx Unspent" value={wallet.unspent} className="tiny" />
          <Statistic title="Tx Spent" value={wallet.spent} className="tiny" />
          <Statistic title="Tx Dead" value={wallet.dead} className="tiny" />
        </Collapse.Panel>
      </Collapse>
      <WhiteSpace size={'md'} />
      <Card title="Wallet">
        <Statistic
          title="Erwartet"
          value={merchantWallet.estimated / 100000000 + ' BTC'}
          suffix={merchantWallet.estimatedUsd + ' €'}
          precision={8}
          className="small suffix"
        />
        <WhiteSpace size={'xs'} />
        <Statistic
          title="Verfügbar"
          value={merchantWallet.spendable / 100000000 + ' BTC'}
          suffix={merchantWallet.spendableUsd + ' €'}
          precision={8}
          className="small suffix"
        />
      </Card>
      <WhiteSpace size={'md'} />
      <Card title="Transaktionen" className="no-padding">
        <Collapse>
          {transactions[0] !== undefined && transactions[0] !== null && transactions[0].expectedAmount !== 0 ? (
            <>
              {transactions.map(item => (
                <>
                  {item.actualAmount != null && (
                    <Collapse.Panel
                      key={item.id}
                      className={getTxHistoryClass(item)}
                      header={
                        <div>
                          <div>
                            <span>{item.amount} €</span>
                            <span>{item.confirmations}</span>
                            <span>{item.transactionType}</span>
                          </div>
                          <div className="float-right">
                            <span>{item.timeAgo}</span>
                          </div>
                        </div>
                      }
                    >
                      <Statistic title="Nutzer" value={item.user} className="tiny" />
                      <Statistic title="Addresse" value={item.address} className="tiny" />
                      <a
                        href="https://live.blockcypher.com/btc-testnet/tx/22a878c2117854a698e856d18f964d006f5a3fe81efa581408aa57a191a2eccb/"
                        target="_blank"
                        rel="noreferrer"
                      >
                        <Statistic title="Transaktionshash" value={item.txHash} className="tiny margin-top" />
                      </a>
                      <Statistic
                        title="Transaktion initiiert"
                        value={dayjs(item.initiatedAt).format('DD.MM.YY HH:mm:ss')}
                        className="tiny"
                      />
                      <Statistic title="Validierung" value={item.confidenceType} className="tiny" />
                      <Statistic title="Bestätigungen" value={item.confirmations} className="tiny" />
                      <WhiteSpace size={'md'} />
                      {item.transactionType !== 'FORWARD' && (
                        <Statistic title="Preis" value={item.amount} suffix={' €'} precision={2} className="tiny" />
                      )}
                      <Statistic title="BTC / Euro" value={item.btcEuro} suffix={' €'} precision={2} className="tiny" />
                      {item.transactionType !== 'FORWARD' ? (
                        <>
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
                          <Statistic
                            title="Servicekosten"
                            value={item.serviceFee / 100000000}
                            suffix={' BTC'}
                            precision={8}
                            className="tiny"
                          />
                        </>
                      ) : (
                        <>
                          <Statistic
                            title="Transaktionskosten"
                            value={item.transactionFee / 100000000}
                            suffix={' BTC'}
                            precision={8}
                            className="tiny"
                          />
                          <Statistic title="Menge" value={item.actualAmount / 100000000} suffix={' BTC'} precision={8} className="tiny" />
                        </>
                      )}
                      <WhiteSpace size={'md'} />
                      <h6>Bestätigungen</h6>
                      {item.confidences.map(conf => (
                        <div key={conf.id} className={conf.confidenceType !== 'CONFIRMED' ? 'conf warning' : 'conf success'}>
                          <span>{dayjs(conf.changeAt).format('DD.MM.YY HH:mm:ss')}</span>
                          <span>
                            {item.transactionType === 'INCOMING_FAST' && conf.confirmations === 0 && conf.confidenceType === 'CONFIRMED'
                              ? 'CONFIRMED_FAST'
                              : conf.confidenceType}
                          </span>
                          <span>{conf.confirmations}</span>
                        </div>
                      ))}
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
  merchantWallet: user.merchantWallet,
  tx: user.tx,
  transactions: user.transactions,
  loading: user.loading,
  errorMessage: user.errorMessage,
});

const mapDispatchToProps = {
  getWallet,
  getTransactions,
  getMerchantWallet,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallet);
