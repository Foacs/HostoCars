import React from 'react';
import PropTypes from 'prop-types';

import { Box, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, IconButton } from '@material-ui/core';
import {
    CheckCircleOutlineRounded as SuccessIcon, CloseRounded as CloseIcon, ErrorOutlineRounded as WarningIcon, HighlightOffRounded as ErrorIcon,
    InfoOutlined as InfoIcon
} from '@material-ui/icons';

import { NOTIFICATION_ERROR_VARIANT, NOTIFICATION_INFO_VARIANT, NOTIFICATION_SUCCESS_VARIANT, NOTIFICATION_WARNING_VARIANT } from 'resources';

import './Notification.scss';

/**
 * Resolves the icon to display in the notification for the given variant.
 * <br />
 * The known variants are : <i>info</i>, <i>success</i>, <i>warning</i> and <i>error</i>.
 * <br />
 * If an unknown variant is given, no icon is returned.
 *
 * @param {string} variant
 *     The notification variant
 *
 * @returns {*} the notification icon
 */
const resolveNotificationIcon = (variant) => {
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
};

/**
 * The notification component.
 *
 * @param {*} [children = undefined]
 *     The component content
 * @param {string} [className = '']
 *     The component class name
 * @param {number} id
 *     The notification ID
 * @param {string} message
 *    The notification message
 * @param {func} onClose
 *    The notification close event action
 * @param {string} [variant=undefined]
 *    The notification variant
 *
 * @constructor
 */
function Notification({
    children,
    className,
    id,
    message,
    onClose,
    variant
}) {
    // Defines the notification panel class name depending on the variant
    const panelClassName = `Panel${variant ? ` Panel_${variant}` : ''}`;

    // Defines the notification title class name depending on the children
    const titleClassName = 'Title' + (children ? ' Title_expandable' : '');

    // Defines the notification icon depending on the variant
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
                {React.cloneElement(children, { id })}
            </ExpansionPanelDetails>}
        </ExpansionPanel>
    </Box>);
}

Notification.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    id: PropTypes.number.isRequired,
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
