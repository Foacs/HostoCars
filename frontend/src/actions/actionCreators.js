import * as TYPES from './actionTypes';

export const logUserInAction = () => dispatch => {
  dispatch({
    type: TYPES.LOG_USER_IN,
  });
};

export const logUserOutAction = () => dispatch => {
  dispatch({
    type: TYPES.LOG_USER_OUT,
  });
};
