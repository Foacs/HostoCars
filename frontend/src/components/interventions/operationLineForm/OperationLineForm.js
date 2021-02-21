import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Checkbox, ExpansionPanel, ExpansionPanelSummary, Grid, IconButton, TextField } from '@material-ui/core';
import { CheckCircleOutline as CheckedIcon, RadioButtonUnchecked as UncheckedIcon, RemoveCircleOutline as DeleteIcon } from '@material-ui/icons';

import { OperationLinePropType, stopPropagation } from 'resources';

import './OperationLineForm.scss';

// Declares the constants
const descriptionFieldLabel = 'Description';
const typeFieldLabel = 'Type';

/**
 * The operation form component.
 *
 * @param [className = '']
 *     The component class name
 * @param isValidationActive
 *     If the validation is active or not
 * @param onDeleteOperationLine
 *     If the list item has to have a border
 * @param operationLine
 *     The operation line of the form
 *
 * @constructor
 */
function OperationLineForm({
    className,
    isValidationActive,
    onDeleteOperationLine,
    operationLine
}) {
    // Initializes the state
    const [ description, setDescription ] = useState(operationLine.description);
    const [ doneFlag, setDoneFlag ] = useState(operationLine.done);
    const [ type, setType ] = useState(operationLine.type);

    // Forces the initialization
    if (description !== operationLine.description) {
        setDescription(operationLine.description);
    }
    if (doneFlag !== operationLine.done) {
        setDoneFlag(operationLine.done);
    }
    if (type !== operationLine.type) {
        setType(operationLine.type);
    }

    // Compute internal values
    const isDescriptionInError = isValidationActive && (null === description || undefined === description || '' === description);
    const isTypeInError = isValidationActive && (null === type || undefined === type || '' === type);

    /**
     * Handles the delete button click action.
     *
     * @param {object} e
     *     The event
     */
    const onDeleteButtonClick = (e) => {
        onDeleteOperationLine();
        stopPropagation(e);
    };

    /**
     * Handles the description change action.
     *
     * @param {object} e
     *     The event
     */
    const onDescriptionChange = (e) => {
        setDescription(e.target.value);
        operationLine.description = e.target.value;
    };

    /**
     * Handles the done flag change action.
     *
     * @param {object} e
     *     The event
     */
    const onDoneFlagChange = (e) => {
        setDoneFlag(e.target.checked);
        operationLine.done = e.target.checked;
    };

    /**
     * Handles the type change action.
     *
     * @param {object} e
     *     The event
     */
    const onTypeChange = (e) => {
        setType(e.target.value);
        operationLine.type = e.target.value;
    };

    return (<ExpansionPanel className={className} elevation={0} expanded={false} id='OperationLineForm'>
        <ExpansionPanelSummary className='OperationLineForm_Summary'>
            <IconButton className='OperationLineForm_Summary_DeleteButton' color='primary' onClick={onDeleteButtonClick}>
                <DeleteIcon className='OperationLineForm_Summary_DeleteButton_Icon' />
            </IconButton>

            <Grid container>
                <Grid item xs={6}>
                    <TextField className='OperationLineForm_Summary_Description' error={isDescriptionInError} InputLabelProps={{ shrink: true }}
                               label={descriptionFieldLabel} onChange={onDescriptionChange} onClick={stopPropagation} required value={description}
                               variant='outlined' />
                </Grid>

                <Grid item xs={6}>
                    <TextField className='OperationLineForm_Summary_Type' error={isTypeInError} InputLabelProps={{ shrink: true }}
                               label={typeFieldLabel} onChange={onTypeChange} onClick={stopPropagation} required value={type} variant='outlined' />
                </Grid>
            </Grid>

            <Checkbox checked={doneFlag} checkedIcon={<CheckedIcon className='OperationLineForm_Summary_DoneFlag_CheckedIcon' />}
                      className='OperationLineForm_Summary_DoneFlag'
                      icon={<UncheckedIcon className='OperationLineForm_Summary_DoneFlag_UncheckedIcon' />} onChange={onDoneFlagChange} />
        </ExpansionPanelSummary>
    </ExpansionPanel>);
}

OperationLineForm.propTypes = {
    className: PropTypes.string,
    isValidationActive: PropTypes.bool.isRequired,
    onDeleteOperationLine: PropTypes.func.isRequired,
    operationLine: OperationLinePropType.isRequired
};

OperationLineForm.defaultProps = {
    className: ''
};

export default OperationLineForm;
