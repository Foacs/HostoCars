import * as TYPES from 'actions/actionTypes';

const initialState = {
    isUserLogged: false,
    contacts: [],
    contactById: undefined,
    searchedContacts: []
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.LOG_USER_IN:
            return {
                ...state,
                isUserLogged: true
            };
        case TYPES.LOG_USER_OUT:
            return {
                ...state,
                isUserLogged: false
            };
        case TYPES.GET_CONTACTS:
            return {
                ...state,
                contacts: action.data
            };
        case TYPES.GET_CONTACT_BY_ID:
            return {
                ...state,
                contactById: action.data
            };
        case TYPES.SEARCH_CONTACTS:
            return {
                ...state,
                searchedContacts: action.data
            };
        case TYPES.SAVE_CONTACT:
            return state;
        case TYPES.UPDATE_CONTACT_BY_ID:
            return state;
        case TYPES.UPDATE_CONTACT_PICTURE_BY_ID:
            return state;
        case TYPES.DELETE_CONTACT_BY_ID:
            return state;
        default:
            return state;
    }
};

export default reducer;
