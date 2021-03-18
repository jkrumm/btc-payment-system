import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { logout } from 'app/shared/reducers/authentication';

import { List, InputItem, WhiteSpace, Slider, Button } from 'antd-mobile';
import { Card, Statistic, Alert, Radio, Divider } from 'antd';
import { createForm } from 'rc-form';
import './transaction.scss';
import { Heading } from 'app/shared/util/ui-components';
import { faHandshake, faMoneyBillAlt } from '@fortawesome/free-regular-svg-icons';
import { initTx, getBtcPrice, getMerchant } from 'app/btc/user.reducer';
import dayjs from 'dayjs';

// 通过自定义 moneyKeyboardWrapProps 修复虚拟键盘滚动穿透问题
// https://github.com/ant-design/ant-design-mobile/issues/307
// https://github.com/ant-design/ant-design-mobile/issues/163
const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent);
let moneyKeyboardWrapProps;
if (isIPhone) {
  moneyKeyboardWrapProps = {
    onTouchStart: e => e.preventDefault(),
  };
}

export interface ITransactionProps extends StateProps, DispatchProps {
  amount: number;
  step: number;
}

export interface INumberInputProps {
  form?: any;
}

export interface INumberInputState {
  type?: any;
}

const cl = log => {
  console.info(log);
};

class H5NumberInputExample extends React.Component<INumberInputProps, INumberInputState> {
  state = {
    type: 'money',
  };
  private inputRef: HTMLInputElement;

  render() {
    const { getFieldProps } = this.props.form;
    const { type } = this.state;
    return (
      <div>
        <List>
          <InputItem
            {...getFieldProps('money3')}
            type={type}
            defaultValue={100}
            placeholder="start from left"
            clear
            moneyKeyboardAlign="left"
            moneyKeyboardWrapProps={moneyKeyboardWrapProps}
          >
            Exakter Betrag
          </InputItem>
        </List>
      </div>
    );
  }
}

import { Steps, WingBlank } from 'antd-mobile';
import set = Reflect.set;

const Step = Steps.Step;

const steps = [
  {
    title: 'Start',
    description: 'Betrag festlegen',
  },
  {
    title: 'Zahlung',
    description: 'Bitcoins versenden',
  },
  {
    title: 'Bestätigung',
    description: 'Bitcoins erhalten',
  },
].map((s, i) => <Step key={i} title={s.title} description={s.description} />);

const customIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 42 42" className="am-icon am-icon-md">
    <g fillRule="evenodd" stroke="transparent" strokeWidth="4">
      <path d="M21 0C9.402 0 0 9.402 0 21c0 11.6 9.402 21 21 21s21-9.4 21-21C42 9.402 32.598 0 21 0z" />
      <path
        fill="#FFF"
        d="M29 18.73c0-.55-.447-1-1-1H23.36l4.428-5.05c.407-.46.407-1.208 0-1.668-.407-.46-1.068-.46-1.476 0l-5.21 5.89-5.21-5.89c-.406-.46-1.067-.46-1.475 0-.406.46-.406 1.207 0 1.667l4.43 5.05H14.23c-.55 0-.998.45-.998 1 0 .554.448.97 1 .97h5.9v3.942h-5.9c-.552 0-1 .448-1 1s.448.985 1 .985h5.9v4.896c0 .552.448 1 1 1 .55 0 .968-.284.968-.836v-5.06H28c.553 0 1-.433 1-.985s-.447-1-1-1h-5.9v-3.94H28c.553 0 1-.418 1-.97z"
      />
    </g>
  </svg>
);

const H5NumberInputExampleWrapper = createForm()(H5NumberInputExample);

const Transaction = (props: ITransactionProps) => {
  const [amount, setAmount] = useState(props.amount);
  const [step, setStep] = useState(props.step);
  const [transactionType, setTransactionType] = useState('fast');
  const { auth, btcPrice, currentTx, serviceFee, tx, merchant } = props;
  const [confirmation, setConfirmation] = useState(tx[0]);
  let inputRef = HTMLInputElement;

  useEffect(() => {
    setAmount(10);
    props.getBtcPrice();
    props.getMerchant();
    console.log(btcPrice);
    console.log(currentTx);
    console.log(tx);

    if (tx.length > 1) {
      tx.forEach(item => {
        if (item.address === currentTx.address) {
          console.log('SUCCESS' + item);
          if (item.confidence.confidenceType === 'BUILDING') {
            setConfirmation(item);
            setStep(2);
          }
          if (item.confidence.confidenceType === 'CONFIRMED') {
            setConfirmation(item);
            setStep(2);
          }
        }
      });
    }
  }, [currentTx, tx]);

  const resetTx = () => {
    props.getBtcPrice();
    props.getMerchant();
    console.log(getBtcPrice());
    console.log(btcPrice);
    setAmount(10);
    setStep(0);
    setTransactionType('fast');
  };

  const onChangeTransactionType = e => {
    console.log('change :' + e.target.value);
    setTransactionType(e.target.value);
  };

  return (
    <div>
      <Heading icon="https://ik.imagekit.io/jtrj8won4m0/BtcPaymentSystem/transaction__q2idGE7pGWVL.svg" heading="Transaktion" />
      <WhiteSpace size="xl" />
      <WingBlank className="stepsExample">
        <Steps current={step} direction="horizontal" size="small">
          {steps}
        </Steps>
      </WingBlank>
      <WhiteSpace size="xl" />
      {step === 0 && (
        <>
          <Card title="Transaktion initiieren">
            <div onClick={() => inputRef.focus()} className="amount">
              <Statistic title="Betrag in Euro" value={amount} suffix={' €'} precision={2} />
            </div>
            <InputItem
              type={'money'}
              placeholder="Bestimmen Sie einen Betrag"
              clear
              value={amount}
              onChange={setAmount}
              ref={el => (inputRef = el)}
              moneyKeyboardAlign="left"
              moneyKeyboardWrapProps={moneyKeyboardWrapProps}
              className="no-display"
            />
            <WhiteSpace size={'lg'} />
            <WhiteSpace size={'lg'} />
            <Slider defaultValue={10} min={0} max={50} value={amount} onChange={setAmount} />
            <WhiteSpace size={'lg'} />
            <Divider />
            <Radio.Group onChange={onChangeTransactionType} defaultValue="fast">
              <Radio.Button value="fast">Schnell</Radio.Button>
              <Radio.Button value="secure">Sicher</Radio.Button>
            </Radio.Group>
            {transactionType === 'fast' ? (
              <div>
                <WhiteSpace size={'md'} />
                <Statistic title="Transaktionsdauer" value={'~1 Minute'} className="small" />
                <Statistic title="Servicekosten" value={merchant.fee.percentSecure} suffix=" %" className="small" />
              </div>
            ) : (
              <div>
                <WhiteSpace size={'md'} />
                <Statistic title="Transaktionsdauer" value={'~15 Minuten'} className="small" />
                <Statistic title="Servicekosten" value={merchant.fee.percent} suffix=" %" className="small" />
              </div>
            )}
            <Divider className="smaller" />
            <div>
              <Statistic title="BTC / EURO" value={btcPrice} suffix={' €'} precision={2} className="small" />
            </div>
          </Card>
          <WhiteSpace size={'lg'} />
          <Button
            onClick={() => {
              cl('initTX amount: ' + amount + ' type: ' + transactionType);
              props.initTx(amount, transactionType);
              setStep(1);
            }}
            type={'primary'}
          >
            Transaktion initiieren
          </Button>
        </>
      )}
      {step === 1 && (
        <>
          <Card title="Bitcoins versenden">
            <img
              alt="example"
              width={'200px'}
              src={
                'https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=bitcoin:' +
                currentTx.address +
                '?amount=' +
                currentTx.expectedAmount / 100000000
              }
            />
            <Statistic title="Bitcoin Adresse" value={currentTx.address} className="tiny" />
            <WhiteSpace size={'md'} />
            <Statistic title="Betrag in Euro" value={currentTx.amount} suffix={' €'} precision={2} className="small" />
            <WhiteSpace size={'md'} />
            <Statistic title="Betrag in BTC" value={currentTx.expectedAmount / 100000000} suffix={' BTC'} precision={8} className="small" />
            <WhiteSpace size={'md'} />
            <Statistic title="Transaktionsart" value={currentTx.transactionType} className="small" />
            <WhiteSpace size={'md'} />
            <Statistic title="Servicegebühren" value={serviceFee} suffix={' %'} precision={2} className="small" />
          </Card>
          <WhiteSpace size={'lg'} />
          <Button onClick={() => resetTx()} className={'footer-button'}>
            Transaktion zurücksetzen
          </Button>
        </>
      )}
      {step === 2 && (
        <>
          <Card title="Bitcoins erhalten">
            {confirmation.confidence.transaction.transactionType === 'INCOMING_FAST' && (
              <>
                {confirmation.confidence.confidenceType === 'CONFIRMED' && (
                  <Alert
                    message="Transaktion abgeschlossen!"
                    description="Transaktion wurde erfolgreich validiert jedoch noch nicht bestätigt."
                    type="success"
                  />
                )}
                {confirmation.confidence.confidenceType === 'BUILDING' && (
                  <Alert
                    message="Transaktion im Mempool!"
                    description="Transaktion wurde gefunden jedoch noch nicht validiert."
                    type="info"
                  />
                )}
              </>
            )}
            {confirmation.confidence.transaction.transactionType === 'INCOMING_SECURE' && (
              <>
                <Alert
                  message="Sichere Transaktion!"
                  description="Die Transaktion wird erst mit dem nächsten Block validiert!"
                  type="error"
                />
                {confirmation.confidence.confidenceType === 'BUILDING' && (
                  <>
                    <WhiteSpace size={'md'} />
                    <Alert
                      message="Transaktion im Mempool!"
                      description="Transaktion wurde gefunden jedoch noch nicht validiert."
                      type="info"
                    />
                  </>
                )}
              </>
            )}
            <WhiteSpace size={'xl'} />
            <Statistic title="Betrag in Euro" value={currentTx.amount} suffix={' €'} precision={2} className="small" />
            <WhiteSpace size={'md'} />
            <Statistic title="Betrag in BTC" value={currentTx.expectedAmount / 100000000} suffix={' BTC'} precision={8} className="small" />
            <Statistic
              title="Erhaltene BTC"
              value={confirmation.confidence.transaction.actualAmount / 100000000}
              suffix={' BTC'}
              precision={8}
              className="small"
            />
            <WhiteSpace size={'md'} />
            <Statistic
              title="Vergangene Zeit"
              value={dayjs(confirmation.confidence.changeAt).diff(currentTx.initiatedAt, 's', true)}
              suffix={' Sekunden'}
              precision={2}
              className="small"
            />
            {confirmation.blockCypher != null && (
              <>
                <WhiteSpace size={'md'} />
                <Statistic title="Double Spend" value={confirmation.blockCypher.double_spend.valueOf() ? 'JA' : 'Nein'} className="small" />
              </>
            )}
          </Card>
          <WhiteSpace size={'lg'} />
          <Button type={'primary'} className={'footer-button'} onClick={() => resetTx()}>
            Neue Transaktion
          </Button>
        </>
      )}
    </div>
  );
};

const mapStateToProps = ({ authentication, user }: IRootState) => ({
  auth: authentication,
  tx: user.tx,
  merchant: user.merchant,
  btcPrice: user.btcPrice,
  currentTx: user.currentTx,
  serviceFee: user.merchant.fee.percent,
});

const mapDispatchToProps = { initTx, getBtcPrice, getMerchant };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Transaction);
