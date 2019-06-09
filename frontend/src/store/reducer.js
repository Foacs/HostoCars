import * as TYPES from 'actions/actionTypes';

const initialState = {
  isUserLogged: false,
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case TYPES.LOG_USER_IN:
      return {
        ...state,
        isUserLogged: true,
      };
    case TYPES.LOG_USER_OUT:
      return {
        ...state,
        isUserLogged: initialState.isUserLogged,
      };
    default:
      return state;
  }
};

export default reducer;
