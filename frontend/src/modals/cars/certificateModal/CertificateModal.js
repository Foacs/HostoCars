import React from 'react';
import Magnifier from 'react-magnifier';
import PropTypes from 'prop-types';

import { Dialog, DialogContent } from '@material-ui/core';

import { ESCAPE_KEY_CODE } from 'resources';

import './CertificateModal.scss';

/**
 * The modal component to display a certificate file with a magnifying glass.
 *
 * @param {*} certificate
 *     The certificate to display
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {boolean} open
 *     If the modal is open
 *
 * @constructor
 */
function CertificateModal({ certificate, className, onClose, open }) {
    /**
     * Handles the key pressed action.
     *
     * @param {object} e
     *     The event
     */
    const onKeyPressed = (e) => {
        if (ESCAPE_KEY_CODE === e.keyCode) {
            e.preventDefault();
            onClose();
        }
    };

    return (<Dialog className={className} id='CertificateModal' onClose={onClose} onKeyDown={onKeyPressed} open={open}>
        <DialogContent className='Content'>
            <Magnifier className='Magnifier' height='100%' mgBorderWidth={3} mgShowOverflow={false} src={`data:image/jpeg;base64,${certificate}`}
                       width='100%' />
        </DialogContent>
    </Dialog>);
}

CertificateModal.propTypes = {
    certificate: PropTypes.node.isRequired,
    className: PropTypes.string,
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired
};

CertificateModal.defaultProps = {
    className: ''
};

export default CertificateModal;
