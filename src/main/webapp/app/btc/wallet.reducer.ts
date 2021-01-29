import axios from 'axios';

export const ACTION_TYPES = {
  WALLET_WEBSOCKET_ACTIVITY_MESSAGE: 'wallet/WALLET_WEBSOCKET_ACTIVITY_MESSAGE',
};

const initialState = {
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
