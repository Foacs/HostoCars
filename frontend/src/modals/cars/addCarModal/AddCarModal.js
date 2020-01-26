import React from 'react';
import PropTypes from 'prop-types';

import { CarForm } from 'components';

import './AddCarModal.scss';

// Defines the form title
const formTitle = 'Ajout d\'une voiture';

/**
 * The modal component to add a new car.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 * @param {string[]} [registrations = []]
 *     The list of existing registrations
 * @param {string[]} [serialNumbers = []]
 *     The list of existing serial numbers
 *
 * @constructor
 */
function AddCarModal({ className, onClose, onValidate, open, registrations, serialNumbers }) {
    return <CarForm onClose={onClose} onValidate={onValidate} open={open} registrations={registrations} serialNumbers={serialNumbers}
                    title={formTitle} />;
}

AddCarModal.propTypes = {
    className: PropTypes.string,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    registrations: PropTypes.arrayOf(PropTypes.string),
    serialNumbers: PropTypes.arrayOf(PropTypes.string)
};

AddCarModal.defaultProps = {
    className: '',
    registrations: [],
    serialNumbers: []
};

export default AddCarModal;
