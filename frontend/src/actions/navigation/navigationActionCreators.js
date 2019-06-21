import { navigationActionTypes as types } from 'actions';

export const changeSelectedMenuIndexAction = index => dispatch => {
    dispatch({
        type: types.CHANGE_SELECTED_MENU_INDEX,
        index
    });
};

export const changeCurrentPageAction = (pageName, pagePath) => dispatch => {
    dispatch({
        type: types.CHANGE_CURRENT_PAGE,
        pageName,
        pagePath
    });
};
