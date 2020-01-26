import { notificationActionTypes as types } from 'actions';

// The reducer's initial state
const initialState = {
    notifications: []
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
const notificationReducer = (action, state = initialState) => {
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
