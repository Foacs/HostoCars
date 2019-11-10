import { navigationActionTypes as types } from 'actions';

/**
 * Returns the action object for the CHANGE_SELECTED_MENU_INDEX action type.
 *
 * @param index
 *     The new index
 *
 * @returns {{index, type}} the action's object
 */
const changeSelectedMenuIndex = index => ({
    index,
    type: types.CHANGE_SELECTED_MENU_INDEX
});

/**
 * Changes the selected menu index.
 *
 * @param index
 *     The new index
 *
 * @returns {function()} the action's function
 */
export const changeSelectedMenuIndexAction = index => {
    return dispatch => {
        dispatch(changeSelectedMenuIndex(index));
    };
};

/**
 * Returns the action object for the CHANGE_CURRENT_PAGE action type.
 *
 * @param pageName
 *     The new page name
 * @param pagePath
 *     The new page path
 *
 * @returns {{pagePath, type, pageName}} the action's object
 */
const changeCurrentPage = (pageName, pagePath) => ({
    pageName,
    pagePath,
    type: types.CHANGE_CURRENT_PAGE
});

/**
 * Updates the breadcrumbs.
 *
 * @param pageName
 *     The new page name
 * @param pagePath
 *     The new page path
 *
 * @returns {function()} the action's function
 */
export const changeCurrentPageAction = (pageName, pagePath) => {
    return dispatch => {
        dispatch(changeCurrentPage(pageName, pagePath));
    };
};
