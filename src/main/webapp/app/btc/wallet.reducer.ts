import axios from 'axios';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  WALLET_WEBSOCKET_ACTIVITY_MESSAGE: 'wallet/WALLET_WEBSOCKET_ACTIVITY_MESSAGE',
  FETCH_WALLET: 'wallet/FETCH_WALLET',
};

const initialState = {
  loading: false,
  errorMessage: null,
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
};

export type WalletState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletState = initialState, action): WalletState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WALLET):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WALLET):
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
