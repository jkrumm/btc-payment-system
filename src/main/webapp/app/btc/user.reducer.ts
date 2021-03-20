import axios from 'axios';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import Transaction from 'app/btc/user/transaction/transaction';

export const ACTION_TYPES = {
  WALLET_WEBSOCKET_ACTIVITY_MESSAGE: 'wallet/WALLET_WEBSOCKET_ACTIVITY_MESSAGE',
  TX_WEBSOCKET_ACTIVITY_MESSAGE: 'wallet/TX_WEBSOCKET_ACTIVITY_MESSAGE',
  FETCH_WALLET: 'wallet/FETCH_WALLET',
  FETCH_MERCHANT: 'user/FETCH_MERCHANT',
  FETCH_MERCHANT_WALLET: 'user/FETCH_MERCHANT_WALLET',
  FETCH_TRANSACTIONS: 'user/FETCH_TRANSACTIONS',
  INIT_TX: 'user/INIT_TX',
  GET_BTC_PRICE: 'user/GET_BTC_PRICE',
  GET_ADDRESS_WALLET: 'user/GET_ADDRESS_WALLET',
  FORWARD: 'user/FORWARD',
};

const initialState = {
  loading: false,
  errorMessage: null,
  btcPrice: 0,
  wallet: {
    blockHeight: 0,
    blockMinedAt: '2021-01-28T10:08:33Z',
    available: 0,
    availableSpendable: 0,
    estimated: 0,
    estimatedSpendable: 0,
    pending: 0,
    unspent: 0,
    spent: 0,
    dead: 0,
  },
  merchant: {
    name: '',
    forward: '',
    email: '',
    fee: {
      feeType: '',
      percent: 0,
      percentSecure: 0,
    },
  },
  addressWallet: {
    address: '',
    total_received: 0,
    total_sent: 0,
    balance: 0,
    unconfirmed_balance: 0,
    final_balance: 0,
    n_tx: 0,
    unconfirmed_n_tx: 0,
    final_n_tx: 0,
  },
  merchantWallet: {
    estimated: 0,
    estimatedUsd: 0,
    spendable: 0,
    spendableUsd: 0,
    serviceFee: 0,
    serviceFeeUsd: 0,
    forward: 0,
    forwardUsd: 0,
    total: 0,
    totalUsd: 0,
  },
  tx: [
    {
      address: '',
      confidence: {
        id: 0,
        confidenceType: '',
        confirmations: 0,
        changeAt: 0,
        transaction: {
          actualAmount: null,
          address: '',
          amount: 0,
          btcEuro: 0,
          expectedAmount: 0,
          initiatedAt: null,
          merchant: {
            id: 0,
            name: '',
            email: '',
          },
          serviceFee: 0,
          transactionFee: 0,
          transactionType: '',
          txHash: '',
          user: {
            id: 0,
            login: '',
          },
        },
      },
      blockCypher: {
        total: 0,
        fees: 0,
        size: 0,
        vsize: 0,
        preference: '',
        double_spend: false,
        confirmations: 0,
        confidence: 0,
      },
    },
  ],
  currentTx: {
    actualAmount: 0,
    address: '',
    amount: 0,
    btcEuro: 0,
    expectedAmount: 0,
    initiatedAt: null,
    merchant: {
      id: 0,
      name: '',
      email: '',
    },
    serviceFee: 0,
    transactionFee: 0,
    transactionType: '',
    txHash: '',
    user: {
      id: 0,
      login: '',
    },
  },
  transactions: [
    {
      id: 0,
      user: '',
      address: '',
      initiatedAt: null,
      actualAmount: 0,
      amount: 0,
      btcEuro: 0,
      expectedAmount: 0,
      confidences: [
        {
          id: 0,
          confidenceType: '',
          confirmations: 0,
          changeAt: 0,
        },
      ],
      serviceFee: 0,
      transactionFee: 0,
      transactionType: '',
      txHash: '',
      timeAgo: '',
      confidenceType: '',
      confirmations: 0,
    },
  ],
  forward: {
    amount: 0,
    to: '',
    txHash: '',
    fee: 0,
    initiatedAt: 0,
  },
};

export type UserState = Readonly<typeof initialState>;

// Reducer

export default (state: UserState = initialState, action): UserState => {
  switch (action.type) {
    case (REQUEST(ACTION_TYPES.FETCH_WALLET),
    REQUEST(ACTION_TYPES.FETCH_MERCHANT),
    REQUEST(ACTION_TYPES.FETCH_MERCHANT_WALLET),
    REQUEST(ACTION_TYPES.FETCH_TRANSACTIONS),
    REQUEST(ACTION_TYPES.INIT_TX),
    REQUEST(ACTION_TYPES.GET_BTC_PRICE),
    REQUEST(ACTION_TYPES.GET_ADDRESS_WALLET),
    REQUEST(ACTION_TYPES.FORWARD)):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case (FAILURE(ACTION_TYPES.FETCH_WALLET),
    FAILURE(ACTION_TYPES.FETCH_MERCHANT),
    FAILURE(ACTION_TYPES.FETCH_MERCHANT_WALLET),
    FAILURE(ACTION_TYPES.FETCH_TRANSACTIONS),
    FAILURE(ACTION_TYPES.INIT_TX),
    FAILURE(ACTION_TYPES.GET_BTC_PRICE),
    FAILURE(ACTION_TYPES.GET_ADDRESS_WALLET),
    FAILURE(ACTION_TYPES.FORWARD)):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLET):
      return {
        ...state,
        loading: false,
        wallet: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MERCHANT):
      return {
        ...state,
        loading: false,
        merchant: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MERCHANT_WALLET):
      return {
        ...state,
        loading: false,
        merchantWallet: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSACTIONS):
      return {
        ...state,
        loading: false,
        transactions: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.INIT_TX):
      return {
        ...state,
        loading: false,
        currentTx: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.GET_BTC_PRICE):
      return {
        ...state,
        loading: false,
        btcPrice: 10000 / action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.GET_ADDRESS_WALLET):
      return {
        ...state,
        loading: false,
        addressWallet: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FORWARD):
      return {
        ...state,
        loading: false,
        forward: action.payload.data,
      };
    case ACTION_TYPES.WALLET_WEBSOCKET_ACTIVITY_MESSAGE:
      return {
        ...state,
        wallet: action.payload,
      };
    case ACTION_TYPES.TX_WEBSOCKET_ACTIVITY_MESSAGE: {
      const index = state.tx.findIndex(tx => tx.address === action.payload.address);
      const stateX = {
        ...state,
        tx: [...state.tx.slice(0, index), action.payload, ...state.tx.slice(index + 1)],
      };
      console.log(stateX);
      return stateX;
    }
    default:
      return state;
  }
};

// Actions

export const getWallet = () => ({
  type: ACTION_TYPES.FETCH_WALLET,
  payload: axios.get('/api/wallet'),
});

export const getMerchant = () => ({
  type: ACTION_TYPES.FETCH_MERCHANT,
  payload: axios.get('/api/user/merchant'),
});

export const getMerchantWallet = () => ({
  type: ACTION_TYPES.FETCH_MERCHANT_WALLET,
  payload: axios.get('/api/user/wallet'),
});

export const getTransactions = () => ({
  type: ACTION_TYPES.FETCH_TRANSACTIONS,
  payload: axios.get('/api/user/transactions'),
});

export const initTx = (amount, type) => ({
  type: ACTION_TYPES.INIT_TX,
  payload: axios.get('/api/user/initTx?amount=' + amount + '&type=' + type),
});

export const getBtcPrice = () => ({
  type: ACTION_TYPES.GET_BTC_PRICE,
  payload: axios.get('https://blockchain.info/tobtc?currency=EUR&value=10000'),
});

export const getAddressWallet = address => ({
  type: ACTION_TYPES.GET_ADDRESS_WALLET,
  payload: axios.get('"https://api.blockcypher.com/v1/btc/test3/addrs/' + address + '/balance?token=4309e288604540068f4395ae1a54a907'),
});

export const sendForward = () => ({
  type: ACTION_TYPES.FORWARD,
  payload: axios.get('/api/user/forward'),
});
