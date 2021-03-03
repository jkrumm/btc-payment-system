import axios from 'axios';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  WALLET_WEBSOCKET_ACTIVITY_MESSAGE: 'wallet/WALLET_WEBSOCKET_ACTIVITY_MESSAGE',
  FETCH_WALLET: 'wallet/FETCH_WALLET',
  FETCH_MERCHANT: 'user/FETCH_MERCHANT',
  INIT_TX: 'user/INIT_TX',
  GET_BTC_PRICE: 'user/GET_BTC_PRICE',
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
    email: '',
    fee: {
      feeType: '',
      percent: 0,
      percentSecure: 0,
    },
  },
  transactions: [],
};

export type UserState = Readonly<typeof initialState>;

// Reducer

export default (state: UserState = initialState, action): UserState => {
  switch (action.type) {
    case (REQUEST(ACTION_TYPES.FETCH_WALLET),
    REQUEST(ACTION_TYPES.FETCH_MERCHANT),
    REQUEST(ACTION_TYPES.INIT_TX),
    REQUEST(ACTION_TYPES.GET_BTC_PRICE)):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case (FAILURE(ACTION_TYPES.FETCH_WALLET),
    FAILURE(ACTION_TYPES.FETCH_MERCHANT),
    FAILURE(ACTION_TYPES.INIT_TX),
    FAILURE(ACTION_TYPES.GET_BTC_PRICE)):
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
    case SUCCESS(ACTION_TYPES.INIT_TX):
      return {
        ...state,
        loading: false,
        transactions: [...state.transactions, action.payload],
      };
    case SUCCESS(ACTION_TYPES.GET_BTC_PRICE):
      console.log('ACTION_TYPES.GET_BTC_PRICE');
      console.log(action.payload);
      return {
        ...state,
        loading: false,
        btcPrice: 10000 / action.payload,
      };
    case ACTION_TYPES.WALLET_WEBSOCKET_ACTIVITY_MESSAGE: {
      return {
        ...state,
        wallet: action.payload,
      };
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

export const initTx = amount => ({
  type: ACTION_TYPES.INIT_TX,
  payload: axios.get('/api/user/initTx/' + amount),
});

export const getBtcPrice = () => ({
  type: ACTION_TYPES.GET_BTC_PRICE,
  payload: axios.get('https://blockchain.info/tobtc?currency=EUR&value=10000'),
});
