import React, { useEffect, useState } from 'react';
import { TabBar } from 'antd-mobile';
import { RocketTwoTone, RocketOutlined, WalletTwoTone, WalletOutlined } from '@ant-design/icons';
import CompBoilerplate from 'app/shared/util/CompBoilerplate';
import Transaction from 'app/btc/user/transaction/transaction';
import './user.scss';
import Wallet from 'app/btc/user/wallet/wallet';
import Profile from 'app/btc/user/profile/profile';
import Forward from 'app/btc/user/forward/forward';

/* interface IWrapUserProps {} */

interface IWrapUserContentProps {
  child: any;
}

const WrapUserContent = (props: IWrapUserContentProps) => {
  return <main>{props.child}</main>;
};

export default () => {
  const [tab, setTab] = useState('transaction');

  useEffect(() => {
    setTab('transaction');
  }, []);

  return (
    <div id="user">
      <TabBar unselectedTintColor="#949494" tintColor="#33A3F4" barTintColor="white">
        <TabBar.Item
          title="Transaktion"
          key="transaction"
          icon={<div className="icon-navbar icon-transaction" />}
          selectedIcon={<div className="icon-navbar active icon-transaction" />}
          selected={tab === 'transaction'}
          onPress={() => {
            setTab('transaction');
          }}
          data-seed="logId"
        >
          <WrapUserContent child={<Transaction amount={10} step={0} />} />
        </TabBar.Item>
        <TabBar.Item
          icon={<div className="icon-navbar icon-wallet" />}
          selectedIcon={<div className="icon-navbar active icon-wallet" />}
          title="Wallet"
          key="wallet"
          selected={tab === 'wallet'}
          onPress={() => {
            setTab('wallet');
          }}
          data-seed="logId1"
        >
          <WrapUserContent child={<Wallet />} />
        </TabBar.Item>
        <TabBar.Item
          icon={<div className="icon-navbar icon-atm" />}
          selectedIcon={<div className="icon-navbar active icon-atm" />}
          title="Auszahlung"
          key="forward"
          selected={tab === 'forward'}
          onPress={() => {
            setTab('forward');
          }}
        >
          <WrapUserContent child={<Forward />} />
        </TabBar.Item>
        <TabBar.Item
          icon={<div className="icon-navbar icon-profile" />}
          selectedIcon={<div className="icon-navbar active icon-profile" />}
          title="Profil"
          key="profile"
          selected={tab === 'profile'}
          onPress={() => {
            setTab('profile');
          }}
        >
          <WrapUserContent child={<Profile />} />
        </TabBar.Item>
      </TabBar>
    </div>
  );
};
