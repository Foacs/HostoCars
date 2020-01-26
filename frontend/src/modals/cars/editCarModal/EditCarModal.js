import React from 'react';
import PropTypes from 'prop-types';

import { CarForm } from 'components';
import { CarPropType } from 'resources';

import './EditCarModal.scss';

// Defines the form title
const formTitle = 'Édition d\'une voiture';

/**
 * The modal component to edit an existing car.
 *
 * @param {object} car
 *     The car to edit
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 * @param {string[]} [registrations = []]
 *     The list of existing registrations (except the one of the current car)
 * @param {string[]} [serialNumbers = []]
 *     The list of existing serial numbers (except the one of the current car)
 *
 * @constructor
 */
function EditCarModal({ car, className, onClose, onValidate, open, registrations, serialNumbers }) {
    return (<CarForm car={car} onClose={onClose} onValidate={onValidate} open={open} registrations={registrations} serialNumbers={serialNumbers}
                     title={formTitle} />);
}

EditCarModal.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string,
    open: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired,
    registrations: PropTypes.arrayOf(PropTypes.string),
    serialNumbers: PropTypes.arrayOf(PropTypes.string)
};

EditCarModal.defaultProps = {
    className: '',
    registrations: [],
    serialNumbers: []
};

export default EditCarModal;
