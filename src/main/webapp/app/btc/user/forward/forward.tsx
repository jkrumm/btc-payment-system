import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';
import { getMerchantWallet } from 'app/btc/user.reducer';

import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert } from 'antd';
import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import './forward.scss';

export interface IProfileProps extends StateProps, DispatchProps {}

const Forward = (props: IProfileProps) => {
  const { merchantWallet } = props;

  useEffect(() => {
    props.getMerchantWallet();
  }, []);

  return (
    <div>
      <Heading icon={'https://ik.imagekit.io/jtrj8won4m0/BtcPaymentSystem/atm_lG3So2-jMt.svg'} heading="Auszahlung" />
      <WhiteSpace size="xl" />
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
    </div>
  );
};

const mapStateToProps = ({ user }: IRootState) => ({
  merchantWallet: user.merchantWallet,
});

const mapDispatchToProps = { getMerchantWallet };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Forward);
