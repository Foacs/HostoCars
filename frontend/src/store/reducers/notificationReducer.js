import { notificationActionTypes as types } from 'actions';

/**
 * Reducer's initial state.
 */
const initialState = {
    notifications: []
};

/**
 * Returns the next reducer's state after the current action.
 *
 * @param {object} state
 *     The current reducer's state
 * @param {object} action
 *     The action
 *
 * @returns {object} the next reducer's state
 */
const notificationReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.DEQUEUE_NOTIFICATION:
            return {
                ...state,
                notifications: state.notifications.map(notification => ((action.dismissAll || notification.key === action.key) ? {
                    ...notification,
                    dismissed: true
                } : { ...notification }))
            };
        case types.ENQUEUE_NOTIFICATION:
            return {
                ...state,
                notifications: [ ...state.notifications, {
                    key: action.key, ...action.notification
                } ]
            };
        default:
            return state;
    }
};

export default notificationReducer;
