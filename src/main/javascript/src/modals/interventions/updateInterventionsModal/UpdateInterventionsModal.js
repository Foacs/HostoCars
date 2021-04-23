import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, IconButton } from '@material-ui/core';
import { AddCircleOutline as CreateIcon, HelpOutlineRounded as HelpIcon } from '@material-ui/icons';

import { BottomBar, InterventionForm } from 'components';
import { APPLY_LABEL, CANCEL_LABEL, CarPropType, ENTER_KEY_CODE, ESCAPE_KEY_CODE, INTERVENTION_STATUS_STEPS } from 'resources';

import './UpdateInterventionsModal.scss';

// Declares the constants
export const instructionsText = `Veuillez modifier les interventions de la voiture actuelle ci-dessous puis cliquer sur '${APPLY_LABEL}'.
                    Les champs annotés du symbole * sont obligatoires.`;
export const titleLabel = 'Édition des interventions';

/**
 * The modal component to update the interventions of an existing car.
 *
 * @param {object} car
 *     The car to update
 * @param {string} [className = '']
 *     The component class name
 * @param {boolean} [isValidationActive]
 *     If the validation is active or not
 * @param {func} onClose
 *     The close event handler
 * @param {func} onEnter
 *     The enter event handler
 * @param {func} onUpdateCar
 *     The update car event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 *
 * @constructor
 */
function UpdateInterventionsModal({
    car,
    className,
    isValidationActive,
    onClose,
    onEnter,
    onUpdateCar,
    onValidate,
    open
}) {
    // Initializes the state
    const [ expandedInterventionIndex, setExpandedInterventionIndex ] = useState(-1);
    const [ help, setHelp ] = useState(false);

    /**
     * Handles the creation of an intervention.
     */
    const onCreateIntervention = () => {
        onUpdateCar({
            ...car,
            interventions: [
                ...car.interventions,
                {
                    operations: [],
                    status: INTERVENTION_STATUS_STEPS[0]
                }
            ]
        });
    };

    /**
     * Handles the creation of an operation.
     *
     * @param {number} x
     *     The index of the intervention on which to create an operation
     */
    const onCreateOperation = (x) => {
        onUpdateCar({
            ...car,
            interventions:
                    car.interventions.map((intervention, i) => i === x ? {
                        ...intervention,
                        operations: [ ...intervention.operations, { operationLines: [] } ]
                    } : intervention)
        });
    };

    /**
     * Handles the creation of an operation line.
     *
     * @param {number} x
     *     The index of the intervention on which to create an operation line
     * @param {number} y
     *     The index of the operation on which to create an operation line
     */
    const onCreateOperationLine = (x, y) => {
        onUpdateCar({
            ...car,
            interventions:
                    car.interventions.map((intervention, i) => i === x ? {
                        ...intervention,
                        operations:
                                intervention.operations.map((operation, j) => j === y ? {
                                    ...operation,
                                    operationLines: [ ...operation.operationLines, { done: false } ]
                                } : operation)
                    } : intervention)
        });
    };

    /**
     * Handles the deletion of an intervention.
     *
     * @param {number} x
     *     The index of the intervention to remove
     */
    const onDeleteIntervention = (x) => {
        onUpdateCar({
            ...car,
            interventions: car.interventions.filter((intervention, i) => i !== x)
        });
    };

    /**
     * Handles the deletion of an operation.
     *
     * @param {number} x
     *     The index of the intervention on which to remove an operation
     * @param {number} y
     *     The index of the operation to remove
     */
    const onDeleteOperation = (x, y) => {
        onUpdateCar({
            ...car,
            interventions:
                    car.interventions.map((intervention, i) => i === x ? {
                        ...intervention,
                        operations: intervention.operations.filter((operation, j) => j !== y)
                    } : intervention)
        });
    };

    /**
     * Handles the deletion of an operation line.
     *
     * @param {number} x
     *     The index of the intervention on which to remove an operation line
     * @param {number} y
     *     The index of the operation on which to remove an operation line
     * @param {number} z
     *     The index of the operation line to remove
     */
    const onDeleteOperationLine = (x, y, z) => {
        onUpdateCar({
            ...car,
            interventions:
                    car.interventions.map((intervention, i) => i === x ? {
                        ...intervention,
                        operations:
                                intervention.operations.map((operation, j) => j === y ? {
                                    ...operation,
                                    operationLines: operation.operationLines.filter((operationLine, k) => k !== z)
                                } : operation)
                    } : intervention)
        });
    };

    /**
     * Handles the help button click action.
     */
    const onHelpButtonClick = () => {
        setHelp(!help);
    };

    /**
     * Handles the key pressed action.
     *
     * @param {object} e
     *     The event
     */
    const onKeyPress = (e) => {
        switch (e.keyCode) {
            case ENTER_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onValidateAction();
                break;
            case ESCAPE_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onClose();
                break;
            default:
                break;
        }
    };

    /**
     * Handles the validation action.
     */
    const onValidateAction = () => {
        onValidate(validateForm(), car);
    };

    const validateForm = () => {
        let isValid = true;

        if (null !== car.interventions && 0 < car.interventions.length) {
            car.interventions.forEach(intervention => {
                if (null === intervention.description || '' === intervention.description) {
                    isValid = false;
                } else if (null !== intervention.operations && 0 < intervention.operations.length) {
                    intervention.operations.forEach(operation => {
                        if (null === operation.label || '' === operation.label) {
                            isValid = false;
                        } else if (null !== operation.operationLines && 0 < operation.operationLines.length) {
                            operation.operationLines.forEach(operationLine => {
                                if (null === operationLine.description || '' === operationLine.description) {
                                    isValid = false;
                                }
                            });
                        } else {
                            isValid = false;
                        }
                    });
                } else {
                    isValid = false;
                }
            });
        }

        return isValid;
    };

    return (<Dialog className={className} id='UpdateInterventionsModal' onClose={onClose} onEnter={onEnter} onKeyDown={onKeyPress} open={open}>
        <DialogTitle className='UpdateInterventionsModal_Title'>
            {titleLabel}

            <IconButton className='UpdateInterventionsModal_Title_HelpButton' color='primary' onClick={onHelpButtonClick}>
                <HelpIcon className='UpdateInterventionsModal_Title_HelpButton_Icon' />
            </IconButton>
        </DialogTitle>

        <DialogContent className='UpdateInterventionsModal_Content'>
            <DialogContentText
                    className={`UpdateInterventionsModal_Content_Instructions${!help && ' UpdateInterventionsModal_Content_Instructions-hidden'}`}>
                <i>{instructionsText}</i>
            </DialogContentText>

            {car.interventions.map((intervention, index) =>
                    <InterventionForm className='UpdateInterventionsModal_Content_InterventionForm'
                                      expanded={expandedInterventionIndex === index} intervention={intervention}
                                      isValidationActive={isValidationActive} key={index}
                                      onClick={() => setExpandedInterventionIndex(expandedInterventionIndex === index ? -1 : index)}
                                      onCreateOperation={() => onCreateOperation(index)}
                                      onCreateOperationLine={i => onCreateOperationLine(index, i)}
                                      onDeleteIntervention={() => onDeleteIntervention(index)}
                                      onDeleteOperation={i => onDeleteOperation(index, i)}
                                      onDeleteOperationLine={(i, j) => onDeleteOperationLine(index, i, j)} />
            )}

            <IconButton className='UpdateInterventionsModal_Content_CreateButton' color='primary' onClick={onCreateIntervention}>
                <CreateIcon className='UpdateInterventionsModal_Content_CreateButton_Icon' />
            </IconButton>
        </DialogContent>

        <DialogActions className='UpdateInterventionsModal_Actions'>
            <Button className='UpdateInterventionsModal_Actions_CancelButton' color='primary' onClick={onClose}>
                {CANCEL_LABEL}
            </Button>

            <Button autoFocus className='UpdateInterventionsModal_Actions_ValidateButton' color='secondary' onClick={onValidateAction}>
                {APPLY_LABEL}
            </Button>
        </DialogActions>

        <BottomBar />
    </Dialog>);
}

UpdateInterventionsModal.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string,
    isValidationActive: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    onEnter: PropTypes.func.isRequired,
    onUpdateCar: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired
};

UpdateInterventionsModal.defaultProps = {
    className: ''
};

export default UpdateInterventionsModal;
