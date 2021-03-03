import { navigationActionTypes as types } from 'actions';

// The reducer's initial state
const initialState = {
    currentNavigationPath: [],
    currentPageName: '',
    menuItems: [],
    selectedMenuIndex: -1
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
const navigationReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.UPDATE_CURRENT_PAGE:
            return {
                ...state,
                currentNavigationPath: action.pagePath,
                currentPageName: action.pageName
            };
        case types.UPDATE_MENU_ITEMS :
            return {
                ...state,
                menuItems: action.items
            };
        case types.UPDATE_SELECTED_MENU_INDEX:
            return {
                ...state,
                selectedMenuIndex: action.index
            };
        default:
            return state;
    }
};

export default navigationReducer;
