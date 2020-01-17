import { notificationActionTypes as types } from 'actions';
import { generateRandomNumber } from 'resources';

/**
 * Returns the action object for the DEQUEUE_NOTIFICATION action type.
 *
 * @param {number} key
 *     The key of the notification to dequeue
 *
 * @returns {{dismissAll: boolean, key: number, type: string}} the action's object
 */
const dequeueNotification = key => ({
    dismissAll: !key,
    key,
    type: types.DEQUEUE_NOTIFICATION
});

/**
 * Dequeues the notification with the given key.
 *
 * @param {number} key
 *     The key of the notification to dequeue
 *
 * @returns {func} the action's function
 */
export const dequeueNotificationAction = key => {
    return dispatch => {
        dispatch(dequeueNotification(key));
    };
};

/**
 * Returns the action object for the {@link ENQUEUE_NOTIFICATION} action type.
 *
 * @param {Object} notification
 *     The notification to enqueue
 * @param {number} key
 *     The notification key
 *
 * @returns {{notification: {notification: Object, key: number}, type: string}} the action's object
 */
const enqueueNotification = (notification, key) => ({
    notification: {
        ...notification,
        key
    },
    type: types.ENQUEUE_NOTIFICATION
});

/**
 * Enqueues the given notification.
 *
 * @param {Object} notification
 *     The notification to enqueue
 *
 * @returns {func} the action's function
 */
export const enqueueNotificationAction = notification => {
    const { options: { key = generateRandomNumber() } = {} } = notification;

    return dispatch => {
        dispatch(enqueueNotification(notification, key));
    };
};
