import React from 'react';
import axios from 'axios';

import { ErrorNotificationContent } from 'components';
import { enqueueNotificationAction } from 'actions';
import { WEB_SERVICE_BASE_URL } from 'resources';

/**
 * Sends a mail.
 *
 * @param {object} mail
 *     The mail to send
 */
export const sendMailAction = mail => {
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
