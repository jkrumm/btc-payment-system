import React, { useEffect, useState } from 'react';
import { TabBar } from 'antd-mobile';
import CompBoilerplate from 'app/shared/util/CompBoilerplate';
import Transaction from 'app/modules/transaction/transaction';
import './user.scss';

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
    <div style={{ position: 'fixed', height: '100vh', width: '100vw', top: 0, zIndex: 999 }}>
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
          <WrapUserContent child={<Transaction amount={10} step={2} />} />
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
          <CompBoilerplate state="wallet" />
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
          <CompBoilerplate state="profile" />
        </TabBar.Item>
      </TabBar>
    </div>
  );
};
