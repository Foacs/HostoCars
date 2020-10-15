import { navigationActionTypes as types } from 'actions';

/**
 * Updates the breadcrumbs.
 *
 * @param {string} pageName
 *     The new page name
 * @param {object} pagePath
 *     The new page path
 */
export const updateCurrentPageAction = (pageName, pagePath) => {
    return dispatch => {
        dispatch(updateCurrentPage(pageName, pagePath));
    };
};

/**
 * Returns the action object for the {@link UPDATE_CURRENT_PAGE} action type.
 *
 * @param {string} pageName
 *     The new page name
 * @param {object} pagePath
 *     The new page path
 *
 * @returns {object} the action object
 */
const updateCurrentPage = (pageName, pagePath) => ({
    pageName,
    pagePath,
    type: types.UPDATE_CURRENT_PAGE
});

/**
 * Updates the application menu items.
 *
 * @param {array} items
 *     The new menu items
 */
export const updateMenuItemsAction = (items) => {
    return dispatch => {
        dispatch(updateMenuItems(items));
    };
};

/**
 * Returns the action object for the {@link UPDATE_MENU_ITEMS} action type.
 *
 * @param {array} items
 *     The new menu items
 *
 * @returns {object} the action object
 */
const updateMenuItems = (items) => ({
    items,
    type: types.UPDATE_MENU_ITEMS
});

/**
 * Updates the selected menu index.
 *
 * @param {number} index
 *     The new index
 */
export const updateSelectedMenuIndexAction = (index) => {
    return dispatch => {
        dispatch(updateSelectedMenuIndex(index));
    };
};

/**
 * Returns the action object for the {@link UPDATE_SELECTED_MENU_INDEX} action type.
 *
 * @param {number} index
 *     The new index
 *
 * @returns {object} the action object
 */
const updateSelectedMenuIndex = (index) => ({
    index,
    type: types.UPDATE_SELECTED_MENU_INDEX
});
