import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';
import { getMerchant, getMerchantWallet } from 'app/btc/user.reducer';

import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert } from 'antd';
import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import './profile.scss';

export interface IProfileProps extends StateProps, DispatchProps {}

const Profile = (props: IProfileProps) => {
  const { account, merchant, merchantWallet } = props;
  console.log(merchant);
  console.log(account);

  useEffect(() => {
    props.getMerchant();
    props.getMerchantWallet();
  }, []);

  return (
    <div>
      <Heading icon={'https://ik.imagekit.io/jtrj8won4m0/BtcPaymentSystem/profile_1tbUqj2YI.svg'} heading="Profil" />
      <WhiteSpace size="xl" />
      <Card title="Nutzer">
        <Statistic title="Benutzername" value={account.login} className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="Name" value={account.firstName + ' ' + account.lastName} className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="E-Mail" value={account.email} className={'small'} />
      </Card>
      <WhiteSpace size={'lg'} />
      <Card title="Händler">
        <Statistic title="Name" value={merchant.name} className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="E-Mail" value={merchant.email} className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="Servicegebühr" value={merchant.fee.feeType} className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="Servicegebühr" value={merchant.fee.percent} suffix=" %" className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="Servicegebühr sicher" value={merchant.fee.percentSecure} suffix=" %" className={'small'} />
        <WhiteSpace size={'xs'} />
        <Statistic title="Angefallene Servicegebühren" value={merchantWallet.serviceFeeUsd} suffix=" €" className={'small'} />
      </Card>
    </div>
  );
};

const mapStateToProps = ({ authentication, user }: IRootState) => ({
  account: authentication.account,
  merchant: user.merchant,
  merchantWallet: user.merchantWallet,
});

const mapDispatchToProps = { logout, getMerchant, getMerchantWallet };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
