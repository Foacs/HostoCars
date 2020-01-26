import { notificationActionTypes as types } from 'actions';
import { generateRandomNumber } from 'resources';

/**
 * Dequeues a notification.
 *
 * @param {number} key
 *     The key of the notification to dequeue
 */
export const dequeueNotificationAction = (key) => {
    return dispatch => {
        dispatch(dequeueNotification(key));
    };
};

/**
 * Returns the action object for the {@link DEQUEUE_NOTIFICATION} action type.
 *
 * @param {number} key
 *     The key of the notification to dequeue
 *
 * @returns {object} the action object
 */
const dequeueNotification = (key) => ({
    dismissAll: !key,
    key,
    type: types.DEQUEUE_NOTIFICATION
});

/**
 * Enqueues a notification.
 *
 * @param {object} notification
 *     The notification to enqueue
 */
export const enqueueNotificationAction = (notification) => {
    const { options: { key = generateRandomNumber() } = {} } = notification;

    return dispatch => {
        dispatch(enqueueNotification(notification, key));
    };
};

/**
 * Returns the action object for the {@link ENQUEUE_NOTIFICATION} action type.
 *
 * @param {object} notification
 *     The notification to enqueue
 * @param {number} key
 *     The notification key
 *
 * @returns {object} the action object
 */
const enqueueNotification = (notification, key) => ({
    notification: {
        ...notification,
        key
    },
    type: types.ENQUEUE_NOTIFICATION
});
