import { navigationActionTypes as types } from 'actions';

const initialState = {
    selectedMenuIndex: -1,
    currentPageName: '',
    currentNavigationPath: []
};

const navigationReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.CHANGE_SELECTED_MENU_INDEX:
            return {
                ...state,
                selectedMenuIndex: action.index
            };
        case types.CHANGE_CURRENT_PAGE:
            return {
                ...state,
                currentPageName: action.pageName,
                currentNavigationPath: action.pagePath
            };
        default:
            return state;
    }
};

export default navigationReducer;
