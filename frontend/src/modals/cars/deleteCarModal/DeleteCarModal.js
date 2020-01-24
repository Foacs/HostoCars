import React from 'react';
import PropTypes from 'prop-types';

import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';

import './DeleteCarModal.scss';

/**
 * Modal to delete an existing car.
 *
 * @param className
 *     The component class name
 * @param onClose
 *     The close event handler
 * @param onValidate
 *     The validate event handler
 * @param open
 *     If the modal is open
 */
function DeleteCarModal({ className, onClose, onValidate, open }) {
    /**
     * Handles the key pressed action.
     *
     * @param e
     *     The event
     */
    const onKeyPressed = e => {
        if (27 === e.keyCode) {
            e.preventDefault();
            onClose();
        }
    };

    /**
     * Handles the validate button click action.
     */
    const onValidateButtonClick = () => {
        onValidate();
    };

    return <Dialog className={className} id='DeleteCarModal' onClose={onClose} onKeyDown={onKeyPressed} open={open}>
        <DialogTitle className='Title'>
            Suppression d'une voiture
        </DialogTitle>

        <DialogContent>
            <DialogContentText className='Instructions'>
                Êtes-vous sûr de vouloir supprimer cette voiture?
            </DialogContentText>
        </DialogContent>

        <DialogActions>
            <Button color='primary' onClick={onClose}>
                Non
            </Button>

            <Button color='secondary' onClick={onValidateButtonClick} autoFocus>
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
