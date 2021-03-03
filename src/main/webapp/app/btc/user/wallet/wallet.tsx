import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { getWallet } from 'app/btc/user.reducer';
import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert, Collapse } from 'antd';
import dayjs from 'dayjs';

import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import './wallet.scss';

export interface ITransactionProps extends StateProps, DispatchProps {}

const Wallet = (props: ITransactionProps) => {
  const { wallet } = props;
  const [timeAgo, setTimeAgo] = useState(dayjs(dayjs(Date.now() - 1).diff(dayjs(wallet.blockMinedAt))));

  useEffect(() => {
    props.getWallet();
    const interval = setInterval(() => {
      const date = new Date();
      date.setHours(date.getHours() - 1);
      setTimeAgo(dayjs(dayjs(date).diff(dayjs(wallet.blockMinedAt))));
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      <Heading icon={faHandshake} heading="Wallet" />
      {props.loading && <h1>LOADING</h1>}
      {props.errorMessage && <Alert message="Error Message" description={props.errorMessage} type="warning" closable />}
      <WhiteSpace size={'xl'} />
      <Card title="Letzter Block">
        <Statistic title="Block Höhe" value={wallet.blockHeight} />
        <WhiteSpace size={'lg'} />
        <Statistic title="Zeit seit letztem Block" value={timeAgo.format('m') + ' Minuten ' + timeAgo.format('s') + ' Sekunden'} />
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
    </div>
  );
};

const mapStateToProps = ({ authentication, user }: IRootState) => ({
  isAuthenticated: authentication.isAuthenticated,
  wallet: user.wallet,
  loading: user.loading,
  errorMessage: user.errorMessage,
});

const mapDispatchToProps = { getWallet };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallet);
