import React from 'react';
import PropTypes from 'prop-types';

import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Typography } from '@material-ui/core';

import { BottomBar } from 'components';
import { ENTER_KEY_CODE, ESCAPE_KEY_CODE, Logo } from 'resources';

import './AboutModal.scss';

/**
 * The about modal component.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {boolean} open
 *     If the modal is open
 */
function AboutModal({
    className,
    onClose,
    open
}) {
    /**
     * Handles the key pressed action.
     *
     * @param {object} e
     *     The event
     */
    const onKeyPressed = (e) => {
        switch (e.keyCode) {
            case ENTER_KEY_CODE:
            case ESCAPE_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onClose();
                break;
            default:
                break;
        }
    };

    return <Dialog className={className} id='AboutModal' onClose={onClose} onKeyDown={onKeyPressed} open={open}>
        <DialogTitle className='Title'>
            À propos
        </DialogTitle>

        <DialogContent>
            <Logo className='Logo non-selectable' />

            <DialogContentText component='div'>
                <Typography align='center' color='textPrimary' variant='h6'>{`Version ${process.env.REACT_APP_VERSION}`}</Typography>
            </DialogContentText>
        </DialogContent>

        <DialogActions>
            <Button color='secondary' onClick={onClose} autoFocus>
                Fermer
            </Button>
        </DialogActions>

        <BottomBar />
    </Dialog>;
}

AboutModal.propTypes = {
    className: PropTypes.string,
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired
};

AboutModal.defaultProps = {
    className: ''
};

export default AboutModal;
