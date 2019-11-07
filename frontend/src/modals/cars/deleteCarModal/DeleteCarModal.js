import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';

import './DeleteCarModal.scss';

function DeleteCarModal({ className, open, onClose, onValidate }) {

    /**
     * Handles the validate button click action.
     */
    const onValidateButtonClick = () => {
        onValidate();
        onClose();
    };

    return <Dialog className={className} id='DeleteCarModal' onClose={onClose} open={open}>
        <DialogTitle className='Title'>
            Suppression d'une voiture
        </DialogTitle>

        <DialogContent className='Content'>
            <DialogContentText className='Content-Instructions'>
                Êtes-vous sûr de vouloir supprimer cette voiture?
            </DialogContentText>
        </DialogContent>

        <DialogActions className='Actions'>
            <Button className='Actions-CancelButton' color='primary' onClick={onClose}>
                Non
            </Button>

            <Button className='Actions-ValidateButton' color='secondary' onClick={onValidateButtonClick} autoFocus>
                Oui
            </Button>
        </DialogActions>
    </Dialog>;
}

DeleteCarModal.propTypes = {
    className: PropTypes.string,
    open: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired
};

DeleteCarModal.defaultProps = {
    className: ''
};

export default DeleteCarModal;
