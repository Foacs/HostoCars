import React, { useState } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, Button, Collapse, List, ListItemText } from '@material-ui/core';
import { MailOutlineRounded as MailIcon } from '@material-ui/icons';

import { sendMailAction } from 'actions';

import './ErrorNotificationContent.scss';

/**
 * Generates an error mail details object from an error's details.
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
 * @returns {object} the generated error mail details object
 */
function generateErrorMail(statusText = '', status = '', message = '', url = '', method = '', timestamp = '') {
    return {
        Error: statusText,
        Code: status,
        Message: message,
        URL: url,
        Method: method,
        Timestamp: timestamp
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
function ErrorNotificationContent({
    className,
    disableMail,
    error,
    sendMail,
    timestamp
}) {
    const {
        config: { url } = {},
        response: {
            config: { method },
            status,
            statusText,
            data
        } = {}
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
