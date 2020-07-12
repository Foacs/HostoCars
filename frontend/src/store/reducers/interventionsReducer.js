import { interventionsActionTypes as types } from 'actions';

// The reducer's initial state
const initialState = {
    interventions: [],
    isGetAllInError: false,
    isGetAllInProgress: false,
    sortedBy: [ 'creationYear', 'number' ]
};

/**
 * Returns the next reducer's state after the current action.
 *
 * @param {string} action
 *     The action
 * @param {object} [state = initialState]
 *     The current reducer's state
 *
 * @returns {object} the next reducer's state
 */
const interventionsReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.CHANGE_INTERVENTIONS_SORT_ORDER:
            return {
                ...state,
                sortedBy: action.sortedBy
            };
        case types.GET_INTERVENTIONS:
            return {
                ...state,
                interventions: initialState.interventions,
                isGetAllInError: initialState.isGetAllInError,
                isGetAllInProgress: true
            };
        case types.GET_INTERVENTIONS_ERROR:
            return {
                ...state,
                interventions: initialState.interventions,
                isGetAllInError: true,
                isGetAllInProgress: initialState.isGetAllInProgress
            };
        case types.GET_INTERVENTIONS_NO_CONTENT:
            return {
                ...state,
                interventions: initialState.interventions,
                isGetAllInError: initialState.isGetAllInError,
                isGetAllInProgress: initialState.isGetAllInProgress
            };
        case types.GET_INTERVENTIONS_OK:
            return {
                ...state,
                interventions: action.interventions,
                isGetAllInError: initialState.isGetAllInError,
                isGetAllInProgress: initialState.isGetAllInProgress
            };
        default:
            return state;
    }
};

export default interventionsReducer;
