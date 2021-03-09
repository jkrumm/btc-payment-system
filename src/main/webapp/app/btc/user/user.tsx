import React, { useEffect, useState } from 'react';
import { TabBar } from 'antd-mobile';
import CompBoilerplate from 'app/shared/util/CompBoilerplate';
import Transaction from 'app/btc/user/transaction/transaction';
import './user.scss';
import Wallet from 'app/btc/user/wallet/wallet';
import Profile from 'app/btc/user/profile/profile';

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
          title="transaction"
          key="transaction"
          icon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://zos.alipayobjects.com/rmsportal/sifuoDUQdAFKAVcFGROC.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          selectedIcon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://zos.alipayobjects.com/rmsportal/iSrlOTqrKddqbOmlvUfq.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          selected={tab === 'transaction'}
          badge={1}
          onPress={() => {
            setTab('transaction');
          }}
          data-seed="logId"
        >
          <WrapUserContent child={<Transaction amount={10} step={0} />} />
        </TabBar.Item>
        <TabBar.Item
          icon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://gw.alipayobjects.com/zos/rmsportal/BTSsmHkPsQSPTktcXyTV.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          selectedIcon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://gw.alipayobjects.com/zos/rmsportal/ekLecvKBnRazVLXbWOnE.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          title="wallet"
          key="wallet"
          badge={'new'}
          selected={tab === 'wallet'}
          onPress={() => {
            setTab('wallet');
          }}
          data-seed="logId1"
        >
          <WrapUserContent child={<Wallet />} />
        </TabBar.Item>
        <TabBar.Item
          icon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://zos.alipayobjects.com/rmsportal/psUFoAMjkCcjqtUCNPxB.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          selectedIcon={
            <div
              style={{
                width: '22px',
                height: '22px',
                background: 'url(https://zos.alipayobjects.com/rmsportal/IIRLrXXrFAhXVdhMWgUI.svg) center center /  21px 21px no-repeat',
              }}
            />
          }
          title="forward"
          key="forward"
          dot
          selected={tab === 'forward'}
          onPress={() => {
            setTab('forward');
          }}
        >
          <CompBoilerplate state="forward" />
        </TabBar.Item>
        <TabBar.Item
          icon={{ uri: 'https://zos.alipayobjects.com/rmsportal/asJMfBrNqpMMlVpeInPQ.svg' }}
          selectedIcon={{ uri: 'https://zos.alipayobjects.com/rmsportal/gjpzzcrPMkhfEqgbYvmN.svg' }}
          title="profile"
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
