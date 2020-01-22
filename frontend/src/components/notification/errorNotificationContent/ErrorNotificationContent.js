import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, Button, Collapse, List, ListItemText } from '@material-ui/core';
import { MailOutlineRounded as MailIcon } from '@material-ui/icons';

import { sendMailAction } from 'actions';
import { ERROR_MAIL_ADDRESS } from 'resources';

import './ErrorNotificationContent.scss';

/**
 * Generates an error mail from an error's details.
 *
 * @param {string} statusText
 *     The error status text
 * @param {number} status
 *     The error status code
 * @param {string} message
 *     The error message
 * @param {string} url
 *     The error URL
 * @param {string} timestamp
 *     The error timestamp
 *
 * @returns {object} the generated error mail object
 */
function generateErrorMail(statusText, status, message, url, timestamp) {
    return {
        recipient: ERROR_MAIL_ADDRESS,
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
                        <tr><th>Error</th><td>${statusText || ''}</td></tr>
                        <tr><th>Code</th><td>${status || ''}</td></tr>
                        <tr><th>Message</th><td>${message || ''}</td></tr>
                        <tr><th>URL</th><td>${url || ''}</td></tr>
                        <tr><th>Time</th><td>${timestamp || ''}</td></tr>
                    </table>
                    <br /><i>Le fichier de log est disponible en pièce jointe.</i>
                </body>
            </html>`,
        attachments: [ './log/log.log' ]
    };
}

/**
 * The application's notification error content component.
 *
 * @param {boolean} disableMail
 *     Flag for disabling the mail button
 * @param {object} error
 *     The error
 * @param {func} sendMail
 *     The {@link sendMailAction} action
 *
 * @constructor
 */
function ErrorNotificationContent({ disableMail, error, sendMail }) {
    // Initializes the displayMail flag
    const [ displayMail, setDisplayMail ] = React.useState(!disableMail);

    const {
        config: { url } = {}, response: { status, statusText, data: { message, timestamp } = {} } = { data: {} }
    } = error;

    // Handles the mail button click action.
    const onMailButtonClick = () => {
        // Sends an error mail with the current error details
        sendMail(generateErrorMail(statusText, status, message, url, timestamp));

        // Hides the mail button
        setDisplayMail(false);
    };

    return (<Box id='ErrorNotificationContent'>
        <List className='DetailList'>
            <ListItemText primary='Error' secondary={statusText} />
            <ListItemText primary='Code' secondary={status} />
            <ListItemText primary='Message' secondary={message} />
            <ListItemText primary='URL' secondary={url} />
            <ListItemText primary='Time' secondary={timestamp} />
        </List>

        <Collapse in={displayMail} timeout={500} unmountOnExit>
            <Button className='MailButton' onClick={onMailButtonClick}>
                Envoyer mail

                <MailIcon className='MailButtonIcon' />
            </Button>
        </Collapse>
    </Box>);
}

const mapDispatchToProps = dispatch => bindActionCreators({
    sendMail: sendMailAction
}, dispatch);

ErrorNotificationContent.propTypes = {
    disableMail: PropTypes.bool,
    error: PropTypes.shape({
        config: PropTypes.shape({ url: PropTypes.string }),
        response: PropTypes.shape({
            status: PropTypes.number,
            statusText: PropTypes.string,
            data: PropTypes.shape({
                message: PropTypes.string,
                timestamp: PropTypes.string
            })
        })
    }).isRequired,
    sendMail: PropTypes.func.isRequired
};

ErrorNotificationContent.defaultProps = {
    disableMail: false
};

export default connect(null, mapDispatchToProps)(ErrorNotificationContent);
