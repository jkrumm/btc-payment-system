import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMerchantUser, defaultValue } from 'app/shared/model/merchant-user.model';

export const ACTION_TYPES = {
  FETCH_MERCHANTUSER_LIST: 'merchantUser/FETCH_MERCHANTUSER_LIST',
  FETCH_MERCHANTUSER: 'merchantUser/FETCH_MERCHANTUSER',
  CREATE_MERCHANTUSER: 'merchantUser/CREATE_MERCHANTUSER',
  UPDATE_MERCHANTUSER: 'merchantUser/UPDATE_MERCHANTUSER',
  DELETE_MERCHANTUSER: 'merchantUser/DELETE_MERCHANTUSER',
  RESET: 'merchantUser/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMerchantUser>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MerchantUserState = Readonly<typeof initialState>;

// Reducer

export default (state: MerchantUserState = initialState, action): MerchantUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MERCHANTUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MERCHANTUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MERCHANTUSER):
    case REQUEST(ACTION_TYPES.UPDATE_MERCHANTUSER):
    case REQUEST(ACTION_TYPES.DELETE_MERCHANTUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MERCHANTUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MERCHANTUSER):
    case FAILURE(ACTION_TYPES.CREATE_MERCHANTUSER):
    case FAILURE(ACTION_TYPES.UPDATE_MERCHANTUSER):
    case FAILURE(ACTION_TYPES.DELETE_MERCHANTUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MERCHANTUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MERCHANTUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MERCHANTUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_MERCHANTUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MERCHANTUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/merchant-users';

// Actions

export const getEntities: ICrudGetAllAction<IMerchantUser> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MERCHANTUSER_LIST,
  payload: axios.get<IMerchantUser>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMerchantUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MERCHANTUSER,
    payload: axios.get<IMerchantUser>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMerchantUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MERCHANTUSER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMerchantUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MERCHANTUSER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMerchantUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MERCHANTUSER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
