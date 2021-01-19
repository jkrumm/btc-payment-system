import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConfidence, defaultValue } from 'app/shared/model/confidence.model';

export const ACTION_TYPES = {
  FETCH_CONFIDENCE_LIST: 'confidence/FETCH_CONFIDENCE_LIST',
  FETCH_CONFIDENCE: 'confidence/FETCH_CONFIDENCE',
  CREATE_CONFIDENCE: 'confidence/CREATE_CONFIDENCE',
  UPDATE_CONFIDENCE: 'confidence/UPDATE_CONFIDENCE',
  DELETE_CONFIDENCE: 'confidence/DELETE_CONFIDENCE',
  RESET: 'confidence/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConfidence>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConfidenceState = Readonly<typeof initialState>;

// Reducer

export default (state: ConfidenceState = initialState, action): ConfidenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONFIDENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONFIDENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONFIDENCE):
    case REQUEST(ACTION_TYPES.UPDATE_CONFIDENCE):
    case REQUEST(ACTION_TYPES.DELETE_CONFIDENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONFIDENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONFIDENCE):
    case FAILURE(ACTION_TYPES.CREATE_CONFIDENCE):
    case FAILURE(ACTION_TYPES.UPDATE_CONFIDENCE):
    case FAILURE(ACTION_TYPES.DELETE_CONFIDENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFIDENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFIDENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONFIDENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONFIDENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONFIDENCE):
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

const apiUrl = 'api/confidences';

// Actions

export const getEntities: ICrudGetAllAction<IConfidence> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONFIDENCE_LIST,
    payload: axios.get<IConfidence>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConfidence> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONFIDENCE,
    payload: axios.get<IConfidence>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConfidence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONFIDENCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConfidence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONFIDENCE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConfidence> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONFIDENCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
