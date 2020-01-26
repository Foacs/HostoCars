import { navigationActionTypes as types } from 'actions';

/**
 * Changes the selected menu index.
 *
 * @param {number} index
 *     The new index
 */
export const changeSelectedMenuIndexAction = (index) => {
    return dispatch => {
        dispatch(changeSelectedMenuIndex(index));
    };
};

/**
 * Returns the action object for the {@link CHANGE_SELECTED_MENU_INDEX} action type.
 *
 * @param {number} index
 *     The new index
 *
 * @returns {object} the action object
 */
const changeSelectedMenuIndex = (index) => ({
    index,
    type: types.CHANGE_SELECTED_MENU_INDEX
});

/**
 * Updates the breadcrumbs.
 *
 * @param {string} pageName
 *     The new page name
 * @param {object} pagePath
 *     The new page path
 */
export const changeCurrentPageAction = (pageName, pagePath) => {
    return dispatch => {
        dispatch(changeCurrentPage(pageName, pagePath));
    };
};

/**
 * Returns the action object for the {@link CHANGE_CURRENT_PAGE} action type.
 *
 * @param {string} pageName
 *     The new page name
 * @param {object} pagePath
 *     The new page path
 *
 * @returns {object} the action object
 */
const changeCurrentPage = (pageName, pagePath) => ({
    pageName,
    pagePath,
    type: types.CHANGE_CURRENT_PAGE
});
