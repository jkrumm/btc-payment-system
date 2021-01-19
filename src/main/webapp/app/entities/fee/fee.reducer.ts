import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFee, defaultValue } from 'app/shared/model/fee.model';

export const ACTION_TYPES = {
  FETCH_FEE_LIST: 'fee/FETCH_FEE_LIST',
  FETCH_FEE: 'fee/FETCH_FEE',
  CREATE_FEE: 'fee/CREATE_FEE',
  UPDATE_FEE: 'fee/UPDATE_FEE',
  DELETE_FEE: 'fee/DELETE_FEE',
  RESET: 'fee/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFee>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FeeState = Readonly<typeof initialState>;

// Reducer

export default (state: FeeState = initialState, action): FeeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FEE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FEE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FEE):
    case REQUEST(ACTION_TYPES.UPDATE_FEE):
    case REQUEST(ACTION_TYPES.DELETE_FEE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FEE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FEE):
    case FAILURE(ACTION_TYPES.CREATE_FEE):
    case FAILURE(ACTION_TYPES.UPDATE_FEE):
    case FAILURE(ACTION_TYPES.DELETE_FEE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FEE):
    case SUCCESS(ACTION_TYPES.UPDATE_FEE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FEE):
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

const apiUrl = 'api/fees';

// Actions

export const getEntities: ICrudGetAllAction<IFee> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FEE_LIST,
  payload: axios.get<IFee>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFee> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FEE,
    payload: axios.get<IFee>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFee> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FEE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFee> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FEE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFee> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FEE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
