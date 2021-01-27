import SockJS from 'sockjs-client';

import Stomp from 'webstomp-client';
import { Storage } from 'react-jhipster';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';
import { List, InputItem, WhiteSpace, Slider, Button } from 'antd-mobile';

import { Heading } from 'app/shared/util/ui-components';
import { faHandshake } from '@fortawesome/free-regular-svg-icons';

/*
const loc = window.location;
const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

const headers = {};
let url = '//' + loc.host + baseHref + '/websocket/wallet';
const authToken = Storage.local.get('jhi-authenticationToken') || Storage.session.get('jhi-authenticationToken');
if (authToken) {
  url += '?access_token=' + authToken;
}
const socket = new SockJS(url);
const stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
  console.log('Connected: ' + frame);
  stompClient.subscribe('/topic/wallet', function (greeting) {
    console.log(JSON.parse(greeting.body).content);
  });
}); */

export interface ITransactionProps extends StateProps, DispatchProps {}

/* const connectWs = () => {
  const loc = window.location;
  const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

  const headers = {};
  let url = '//' + loc.host + baseHref + '/websocket/wallet';
  const authToken = Storage.local.get('jhi-authenticationToken') || Storage.session.get('jhi-authenticationToken');
  if (authToken) {
    url += '?access_token=' + authToken;
  }
  const socket = new SockJS(url);
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/wallet', function (greeting) {
      console.log(JSON.parse(greeting.body).content);
    });
  });
} */
/*
const disconnectWs = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log("Disconnected");
}

const sendWs = msg => {
  console.log("Send WS " + msg);
  stompClient?.send("/topic/wallet", JSON.stringify({'name': msg}));
} */

const TestWebsocket = (props: ITransactionProps) => {
  // const { wallet } = props;
  // const [amount, setAmount] = useState(props.amount);

  /* useEffect(() => { <Button onClick={disconnectWs}>Disconnect</Button>
      <Button onClick={() => sendWs("test")}>Send</Button>
    </div>
    setAmount(10);
  }, []); */

  console.log(props.wallet.activities);

  return (
    <div>
      <WhiteSpace size="lg" />
      <Heading icon={faHandshake} heading="Test Websocket" />
      {props.isAuthenticated && 'Authenticated'}
      {props.wallet.activities.map((item, index) => (
        <div key={index}>
          {index} / {item.msg}
        </div>
      ))}
      {props.wallet.activities.toString()}
      {props.wallet.activities.toString()}
      {props.wallet.activities.length}
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

export default connect(mapStateToProps, mapDispatchToProps)(TestWebsocket);
