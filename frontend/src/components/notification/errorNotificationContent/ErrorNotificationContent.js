import React, { useState } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, Button, Collapse, List, ListItemText } from '@material-ui/core';
import { MailOutlineRounded as MailIcon } from '@material-ui/icons';

import { sendMailAction } from 'actions';
import { LOG_FILE_PATH, SUPPORT_EMAIL_ADDRESS } from 'resources';

import './ErrorNotificationContent.scss';

/**
 * Generates an error mail from an error's details.
 *
 * @param {string} [statusText = '']
 *     The error status text
 * @param {number} [status = '']
 *     The error status code
 * @param {string} [message = '']
 *     The error message
 * @param {string} [url = '']
 *     The error URL
 * @param {string} [method = '']
 *     The error method
 * @param {string} [timestamp = '']
 *     The error timestamp
 *
 * @returns {object} the generated error mail object
 */
function generateErrorMail(statusText = '', status = '', message = '', url = '', method = '', timestamp = '') {
    return {
        recipient: SUPPORT_EMAIL_ADDRESS,
        subject: 'Notification HostoCars - ERREUR',
        content: `<html lang='fr'>
                <head>
                    <title>HostoCars Erreur</title>
                    <style>
                        table {border: 1px solid black;}
                        th, td {border: 1px solid black; padding: 2px 6px;}
                    </style>
                </head>
                <body>
                    <h3>Une erreur est survenue dans HostoCars :</h3>
                    <table>
                        <tr><th>Erreur</th><td>${statusText || ''}</td></tr>
                        <tr><th>Code</th><td>${status || ''}</td></tr>
                        <tr><th>Message</th><td>${message || ''}</td></tr>
                        <tr><th>URL</th><td>${url || ''}</td></tr>
                        <tr><th>Méthode</th><td>${method || ''}</td></tr>
                        <tr><th>Date</th><td>${timestamp || ''}</td></tr>
                    </table>
                    <br />
                    <i>Le fichier de log est disponible en pièce jointe.</i>
                </body>
            </html>`,
        attachmentArray: [ LOG_FILE_PATH ]
    };
}

/**
 * The notification error content component.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {boolean} [disableMail = false]
 *     If the mail button is disabled
 * @param {object} error
 *     The error
 * @param {func} sendMail
 *     The {@link sendMailAction} action
 * @param {object} timestamp
 *     The timestamp
 *
 * @constructor
 */
function ErrorNotificationContent({ className, disableMail, error, sendMail, timestamp }) {
    const {
        config: { url } = {}, response: { config: { method }, status, statusText, data } = {}
    } = error;

    // Initializes the displayMail flag
    const [ displayMail, setDisplayMail ] = useState(!disableMail);

    const upperCaseMethod = method.toUpperCase();

    /**
     * Handles the mail button click action.
     */
    const onMailButtonClick = () => {
        // Sends an error mail with the current error details
        sendMail(generateErrorMail(statusText, status, data, url, upperCaseMethod, timestamp));

        // Hides the mail button
        setDisplayMail(false);
    };

    return (<Box className={className} id='ErrorNotificationContent'>
        <List className='DetailList'>
            <ListItemText primary='Erreur' secondary={statusText} />
            <ListItemText primary='Code' secondary={status} />
            <ListItemText primary='Message' secondary={data} />
            <ListItemText primary='URL' secondary={url} />
            <ListItemText primary='Méthode' secondary={upperCaseMethod} />
            <ListItemText primary='Date' secondary={timestamp} />
        </List>

        <Collapse in={displayMail} timeout={500} unmountOnExit>
            <Button className='MailButton' onClick={onMailButtonClick}>
                Envoyer mail

                <MailIcon className='MailButtonIcon' />
            </Button>
        </Collapse>
    </Box>);
}

const mapDispatchToProps = (dispatch) => bindActionCreators({
    sendMail: sendMailAction
}, dispatch);

ErrorNotificationContent.propTypes = {
    className: PropTypes.string,
    disableMail: PropTypes.bool,
    error: PropTypes.shape({
        config: PropTypes.shape({ url: PropTypes.string }),
        response: PropTypes.shape({
            config: PropTypes.shape({ method: PropTypes.string }),
            data: PropTypes.string,
            status: PropTypes.number,
            statusText: PropTypes.string
        })
    }).isRequired,
    sendMail: PropTypes.func.isRequired,
    timestamp: PropTypes.object.isRequired
};

ErrorNotificationContent.defaultProps = {
    className: '',
    disableMail: false
};

export default connect(null, mapDispatchToProps)(ErrorNotificationContent);
