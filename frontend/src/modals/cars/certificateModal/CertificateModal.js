import { Dialog, DialogContent } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';
import Magnifier from 'react-magnifier';

import './CertificateModal.scss';

function CertificateModal({ className, certificate, onClose, open }) {
    return <Dialog className={className} id='CertificateModal' onClose={onClose} open={open}>
        <DialogContent className='Content'>
            <Magnifier className='Content-Magnifier' height='100%' mgBorderWidth={3} mgShowOverflow={false}
                       src={`data:image/jpeg;base64,${certificate}`} width='100%' />
        </DialogContent>
    </Dialog>;
}

CertificateModal.propTypes = {
    certificate: PropTypes.any, // TODO Put BLOB as type
    className: PropTypes.string,
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired
};

CertificateModal.defaultProps = {
    certificate: undefined,
    className: ''
};

export default CertificateModal;
