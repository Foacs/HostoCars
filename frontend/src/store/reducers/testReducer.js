import { testActionTypes as types } from 'actions';

const initialState = {
    isUserLogged: false,
    contacts: [],
    contactById: undefined,
    searchedContacts: []
};

const testReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.LOG_USER_IN:
            return {
                ...state,
                isUserLogged: true
            };
        case types.LOG_USER_OUT:
            return {
                ...state,
                isUserLogged: false
            };
        case types.GET_CONTACTS:
            return {
                ...state,
                contacts: action.data
            };
        case types.GET_CONTACT_BY_ID:
            return {
                ...state,
                contactById: action.data
            };
        case types.SEARCH_CONTACTS:
            return {
                ...state,
                searchedContacts: action.data
            };
        case types.SAVE_CONTACT:
            return state;
        case types.UPDATE_CONTACT_BY_ID:
            return state;
        case types.UPDATE_CONTACT_PICTURE_BY_ID:
            return state;
        case types.DELETE_CONTACT_BY_ID:
            return state;
        default:
            return state;
    }
};

export default testReducer;
