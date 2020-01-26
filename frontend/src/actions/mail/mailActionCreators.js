import React from 'react';
import axios from 'axios';

import { enqueueNotificationAction } from 'actions';
import { ErrorNotificationContent } from 'components';
import { WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Sends a mail and returns the action promise.
 * <br/>
 * If the operation is successful, a success notification is shown.
 * <br/>
 * If the operation fails, an error notification is shown.
 *
 * @param {object} mail
 *     The mail to send
 *
 * @returns {Promise} the action promise
 */
export const sendMailAction = (mail) => {
    return (dispatch) => {
        return axios.post(`${WEB_SERVICE_BASE_URL}/mail/send`, mail)
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
                        content: <ErrorNotificationContent disableMail error={e} />,
                        persist: true,
                        variant: 'error'
                    }
                }));
            });
    };
};
