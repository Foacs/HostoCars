import { navigationActionTypes as types } from 'actions';

/**
 * Reducer's initial state.
 */
const initialState = {
    currentNavigationPath: [],
    currentPageName: '',
    selectedMenuIndex: -1
};

/**
 * Returns the next reducer's state after the current action.
 *
 * @param state
 *     The current reducer's state
 * @param action
 *     The action
 *
 * @returns {object} the next reducer's state
 */
const navigationReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.CHANGE_CURRENT_PAGE:
            return {
                ...state,
                currentNavigationPath: action.pagePath,
                currentPageName: action.pageName
            };
        case types.CHANGE_SELECTED_MENU_INDEX:
            return {
                ...state,
                selectedMenuIndex: action.index
            };
        default:
            return state;
    }
};

export default navigationReducer;
