import axios from 'axios';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  WEBSOCKET_ACTIVITY_MESSAGE: 'administration/WEBSOCKET_ACTIVITY_MESSAGE',
};

const initialState = {
  wallet: {
    activities: [],
  },
};

export type WalletState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletState = initialState, action): WalletState => {
  switch (action.type) {
    case ACTION_TYPES.WEBSOCKET_ACTIVITY_MESSAGE: {
      // filter out activities from the same session
      const uniqueActivities = state.wallet.activities.filter(activity => activity.sessionId !== action.payload.sessionId);
      // remove any activities with the page of logout
      const activities = [...uniqueActivities, action.payload].filter(activity => activity.page !== 'logout');
      return {
        ...state,
        wallet: { activities },
      };
    }
    default:
      return state;
  }
};

// Actions
