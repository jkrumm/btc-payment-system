import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert } from 'antd';
import dayjs from 'dayjs';

import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';
import TimeAgo from 'react-timeago';
import frenchStrings from 'react-timeago/lib/language-strings/fr';
import buildFormatter from 'react-timeago/lib/formatters/buildFormatter';

const formatter = value => {
  return dayjs(value).format('mm-ss');
};

import './wallet.scss';

export interface ITransactionProps extends StateProps, DispatchProps {}

const Wallet = (props: ITransactionProps) => {
  const { wallet } = props;
  const [timeAgo, setTimeAgo] = useState(dayjs(dayjs(Date.now()).diff(dayjs(wallet.blockMinedAt))));

  useEffect(() => {
    // setInterval(setTimeAgo(() => dayjs(wallet.blockMinedAt.fromNow()).toString()), 1000)
    const interval = setInterval(() => {
      setTimeAgo(dayjs(dayjs(Date.now()).diff(dayjs(wallet.blockMinedAt))));
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  console.log(wallet);

  // console.log(wallet.blockMinedAt)

  return (
    <div>
      <Heading icon={faHandshake} heading="Wallet" />
      <WhiteSpace size={'xl'} />
      <Card title="Letzter Block">
        <Statistic title="Block Höhe" value={wallet.blockHeight} />
        <WhiteSpace size={'lg'} />
        <Statistic title="Zeit seit letztem Block" value={timeAgo.format('m') + ' Minuten ' + timeAgo.format('s') + ' Sekunden'} />
      </Card>
      <WhiteSpace size={'md'} />
      {props.isAuthenticated && 'Authenticated'}
      <br />
      <Statistic title="Available" value={wallet.available} />
      <Statistic title="Available spendable" value={wallet.availableSpendable} />
      <Statistic title="Estimated" value={wallet.estimated} />
      <Statistic title="Estimated Spendable" value={wallet.estimatedSpendable} />
      <Statistic title="Tx Pending" value={wallet.pendingAmount} />
      <Statistic title="Tx Unspent" value={wallet.unspentAmount} />
      <Statistic title="Tx Spent" value={wallet.spent} />
      <Statistic title="Tx Dead" value={wallet.dead} />
    </div>
  );
};

const mapStateToProps = ({ authentication, wallet }: IRootState) => ({
  isAuthenticated: authentication.isAuthenticated,
  wallet: wallet.wallet,
});

const mapDispatchToProps = {};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallet);
