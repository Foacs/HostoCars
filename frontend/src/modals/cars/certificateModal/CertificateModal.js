import React from 'react';
import Magnifier from 'react-magnifier';
import PropTypes from 'prop-types';

import { Dialog, DialogContent } from '@material-ui/core';

import './CertificateModal.scss';

/**
 * Modal to display a certificate file with a magnifying glass.
 *
 * @param className
 *     The component class name
 * @param certificate
 *     The certificate to display
 * @param onClose
 *     The close event handler
 * @param open
 *     If the modal is open
 */
function CertificateModal({ className, certificate, onClose, open }) {
    return <Dialog className={className} id='CertificateModal' onClose={onClose} open={open}>
        <DialogContent className='Content'>
            <Magnifier className='Magnifier' height='100%' mgBorderWidth={3} mgShowOverflow={false} src={`data:image/jpeg;base64,${certificate}`}
                       width='100%' />
        </DialogContent>
    </Dialog>;
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
