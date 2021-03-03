import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';
import { getMerchant } from 'app/btc/user.reducer';

import { WhiteSpace } from 'antd-mobile';
import { Card, Statistic, Alert } from 'antd';
import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

import './profile.scss';

export interface IProfileProps extends StateProps, DispatchProps {}

const Profile = (props: IProfileProps) => {
  const { account, merchantUser, merchant } = props;

  console.log(merchantUser);
  console.log(merchant);
  console.log(account);

  useEffect(() => {
    props.getMerchant();
  }, []);

  return (
    <div>
      <Heading icon={faHandshake} heading="Profil" />
      <WhiteSpace size="xl" />
      <Card title="Nutzer Details">
        <Statistic title="Benutzername" value={account.login} className={'small'} />
        <WhiteSpace size={'lg'} />
        <Statistic title="Name" value={account.firstName + ' ' + account.lastName} className={'small'} />
        <WhiteSpace size={'lg'} />
        <Statistic title="E-Mail" value={account.email} className={'small'} />
      </Card>
      <WhiteSpace size={'lg'} />
      <Card title="Händler Details">
        <Statistic title="Name" value={merchant.name} className={'small'} />
        <WhiteSpace size={'lg'} />
        <Statistic title="E-Mail" value={merchant.email} className={'small'} />
        <WhiteSpace size={'lg'} />
        <Statistic title="Gebühr" value={merchant.fee.feeType} className={'small'} />
        <WhiteSpace size={'md'} />
        <Statistic title="Gebühr prozent" value={merchant.fee.percent} className={'small'} />
        <WhiteSpace size={'md'} />
        <Statistic title="Gebühr prozent sicher" value={merchant.fee.percentSecure} className={'small'} />
      </Card>
    </div>
  );
};

const mapStateToProps = ({ authentication, merchantUser, merchant, user }: IRootState) => ({
  account: authentication.account,
  merchantUser,
  merchant: user.merchant,
});

const mapDispatchToProps = { logout, getMerchant };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
