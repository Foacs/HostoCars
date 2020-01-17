import React from 'react';
import PropTypes from 'prop-types';

import { Box, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, IconButton } from '@material-ui/core';
import {
    CloseRounded as CloseIcon,
    HighlightOffRounded as ErrorIcon,
    InfoOutlined as InfoIcon,
    CheckCircleOutlineRounded as SuccessIcon,
    ErrorOutlineRounded as WarningIcon
} from '@material-ui/icons';

import { NOTIFICATION_ERROR_VARIANT, NOTIFICATION_INFO_VARIANT, NOTIFICATION_SUCCESS_VARIANT, NOTIFICATION_WARNING_VARIANT } from 'resources';

import './Notification.scss';

/**
 * Resolves the icon to display in the notification for the given variant.
 *
 * @param variant
 *     The notification variant
 *
 * @returns {*} the notification icon
 */
function resolveNotificationIcon(variant) {
    const className = 'VariantIcon';

    switch (variant) {
        case NOTIFICATION_ERROR_VARIANT:
            return <ErrorIcon className={className} />;
        case NOTIFICATION_INFO_VARIANT:
            return <InfoIcon className={className} />;
        case NOTIFICATION_SUCCESS_VARIANT:
            return <SuccessIcon className={className} />;
        case NOTIFICATION_WARNING_VARIANT:
            return <WarningIcon className={className} />;
        default:
            return null;
    }
}

/**
 * The application's notification component.
 *
 * @param {*} [children=undefined]
 *     The component content
 * @param {string} [className='']
 *     The component class name
 * @param {string} message
 *    The notification message
 * @param {func} onClose
 *    The notification close event action
 * @param {string} [variant=undefined]
 *    The notification variant
 *
 * @constructor
 */
function Notification({ children, className, message, onClose, variant }) {
    // Defines the notification panel class name depending on the variant.
    const panelClassName = 'Panel' + (variant ? ' Panel_' + variant : '');

    // Defines the notification title class name depending on the children.
    const titleClassName = 'Title' + (children ? ' Title_expandable' : '');

    // Defines the notification icon depending on the variant.
    const notificationIcon = resolveNotificationIcon(variant);

    return (<Box className={className} id='Notification'>
        <ExpansionPanel className={panelClassName}>
            <ExpansionPanelSummary className={titleClassName}>
                {notificationIcon}

                <span className='Message'>{message}</span>

                <IconButton className='CloseButton' onClick={onClose}>
                    <CloseIcon />
                </IconButton>
            </ExpansionPanelSummary>

            {children && <Divider variant='middle' />}

            {children && <ExpansionPanelDetails className='Content'>
                {children}
            </ExpansionPanelDetails>}
        </ExpansionPanel>
    </Box>);
}

Notification.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    message: PropTypes.string.isRequired,
    onClose: PropTypes.func.isRequired,
    variant: PropTypes.string
};

Notification.defaultProps = {
    children: undefined,
    className: '',
    variant: undefined
};

export default Notification;
