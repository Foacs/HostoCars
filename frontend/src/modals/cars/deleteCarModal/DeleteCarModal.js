import React from 'react';
import PropTypes from 'prop-types';

import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';

import { BottomBar } from 'components';
import { ESCAPE_KEY_CODE } from 'resources';

import './DeleteCarModal.scss';

/**
 * The modal component to delete an existing car.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 */
function DeleteCarModal({ className, onClose, onValidate, open }) {
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

            <Button color='secondary' onClick={onValidate} autoFocus>
                Oui
            </Button>
        </DialogActions>

        <BottomBar />
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
