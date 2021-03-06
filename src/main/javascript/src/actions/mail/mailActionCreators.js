import React from 'react';
import axios from 'axios';

import { enqueueNotificationAction } from 'actions';
import { ErrorNotificationContent } from 'components';
import { WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Sends a mail and returns the action promise.
 * <br />
 * If the operation is successful, a success notification is shown.
 * <br />
 * If the operation fails, an error notification is shown.
 *
 * @param {object} details
 *     The details of the mail to send
 *
 * @returns {Promise} the action promise
 */
export const sendMailAction = (details) => {
    return (dispatch) => {
        return axios.put(`${WEB_SERVICE_BASE_URL}/mails`, details)
                .then(() => {
                    dispatch(enqueueNotificationAction({
                        message: 'Mail envoyé avec succès.',
                        options: {
                            variant: 'success'
                        }
                    }));
                })
                .catch(e => {
                    dispatch(enqueueNotificationAction({
                        message: 'Une erreur est survenue lors de l\'envoi du mail.',
                        options: {
                            content: <ErrorNotificationContent disableMail error={e} timestamp={new Date().toLocaleString()} />,
                            persist: true,
                            variant: 'error'
                        }
                    }));
                });
    };
};
