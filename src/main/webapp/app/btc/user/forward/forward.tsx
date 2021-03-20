import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';
import { getMerchantWallet, sendForward } from 'app/btc/user.reducer';

import { WhiteSpace, Button } from 'antd-mobile';
import { Card, Statistic, Collapse } from 'antd';
import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';
import dayjs from 'dayjs';
import './forward.scss';

export interface IProfileProps extends StateProps, DispatchProps {}

const Forward = (props: IProfileProps) => {
  const { merchantWallet, merchant, forward, transactions } = props;

  useEffect(() => {
    props.getMerchantWallet();
  }, []);

  return (
    <div>
      <Heading icon={'https://ik.imagekit.io/jtrj8won4m0/BtcPaymentSystem/atm_lG3So2-jMt.svg'} heading="Auszahlung" />
      <WhiteSpace size="xl" />
      <Card title="Wallet">
        <Statistic title="Auszahlung Addresse" value={merchant.forward} className="small small-text" />
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
      <WhiteSpace size="lg" />
      <Button onClick={() => props.sendForward()} type={'primary'}>
        Zahle verfügbare Bitcoin aus. {merchantWallet.spendableUsd} €
      </Button>
      {forward.amount !== 0 && (
        <div>
          <WhiteSpace size={'lg'} />
          <Card title="Letzte Auszahlung">
            <Statistic title="Zeitpunkt" value={dayjs(forward.initiatedAt).format('DD.MM.YY HH:mm:ss')} className="small" />
            <Statistic
              title="Menge"
              value={forward.amount / 100000000 + ' BTC'}
              suffix={merchantWallet.spendableUsd + ' €'}
              precision={8}
              className="small suffix"
            />
          </Card>
        </div>
      )}
      <WhiteSpace size={'xl'} />
      <Card title="Auszahlungen" className="no-padding">
        <Collapse>
          {transactions[0] !== undefined && transactions[0] !== null && transactions[0].expectedAmount !== 0 ? (
            <>
              {transactions.map(item => (
                <>
                  {item.actualAmount != null && item.transactionType === 'FORWARD' && (
                    <Collapse.Panel
                      key={item.id}
                      className="primary"
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

const mapStateToProps = ({ user }: IRootState) => ({
  merchantWallet: user.merchantWallet,
  merchant: user.merchant,
  forward: user.forward,
  transactions: user.transactions,
});

const mapDispatchToProps = { getMerchantWallet, sendForward };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Forward);
