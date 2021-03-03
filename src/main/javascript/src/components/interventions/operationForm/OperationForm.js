import React, { useState } from 'react';
import PropTypes from 'prop-types';

import {
    Box, Chip, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, IconButton, TextField, Typography
} from '@material-ui/core';
import { AddCircleOutline as CreateIcon, ExpandMoreRounded as ExpandIcon, RemoveCircleOutline as DeleteIcon } from '@material-ui/icons';

import { OperationLineForm } from 'components';
import { OperationPropType, stopPropagation } from 'resources';

import './OperationForm.scss';

// Declares the constants
const descriptionFieldLabel = 'Description';
const linesTitleLabel = 'Lignes d\'opération *';

/**
 * The operation form component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
 * @param isValidationActive
 *     If the validation is active or not
 * @param operation
 *     The operation of the form
 * @param onClick
 *     The action to trigger when clicking on the panel
 * @param onCreateOperationLine
 *     The action to trigger when creating an operation line
 * @param onDeleteOperation
 *     The action to trigger when deleting an operation
 * @param onDeleteOperationLine
 *     The action to trigger when deleting an operation line
 *
 * @constructor
 */
function OperationForm({
    className,
    expanded,
    isValidationActive,
    operation,
    onClick,
    onCreateOperationLine,
    onDeleteOperation,
    onDeleteOperationLine
}) {
    // Initializes the state
    const [ label, setLabel ] = useState(operation.label);

    // Forces the initialization
    if (label !== operation.label) {
        setLabel(operation.label);
    }

    // Compute internal values
    const finishedLines = operation.operationLines.filter(line => line.done).length;
    const totalLines = operation.operationLines.length;
    const isFinished = finishedLines === totalLines;

    const linesStatusLabel = `${finishedLines} ⋮ ${totalLines}`;

    const isLabelInError = isValidationActive && (null === label || undefined === label || '' === label);
    const areLinesInError = isValidationActive && (null === operation.operationLines || undefined === operation.operationLines
            || 0 === operation.operationLines.length);

    /**
     * Handles the delete button click action.
     *
     * @param {object} e
     *     The event
     */
    const onDeleteButtonClick = (e) => {
        onDeleteOperation();
        stopPropagation(e);
    };

    /**
     * Handles the label change action.
     *
     * @param {object} e
     *     The event
     */
    const onLabelChange = (e) => {
        setLabel(e.target.value);
        operation.label = e.target.value;
    };

    return (<ExpansionPanel className={className} elevation={0} expanded={expanded} id='OperationForm' onChange={onClick}>
        <ExpansionPanelSummary className='OperationForm_Summary' expandIcon={<ExpandIcon className='OperationForm_Summary_ExpandIcon' />}>
            <IconButton className='OperationForm_Summary_DeleteButton' color='primary' onClick={onDeleteButtonClick}>
                <DeleteIcon className='OperationForm_Summary_DeleteButton_Icon' />
            </IconButton>

            <TextField className='OperationForm_Summary_LabelField' error={isLabelInError} InputLabelProps={{ shrink: true }}
                       label={descriptionFieldLabel} onChange={onLabelChange} onClick={stopPropagation} required value={label} variant='outlined' />

            <Chip className='OperationForm_Summary_LinesStatus' color={isFinished ? 'secondary' : 'primary'} label={linesStatusLabel} size='small'
                  variant={isFinished ? 'outlined' : 'default'} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='OperationForm_Content'>
            <Grid container>
                <Grid item xs={12}>
                    <Typography align='center' className='OperationForm_Content_LinesTitle' color={areLinesInError ? 'error' : 'initial'}
                                noWrap variant='overline'>
                        {linesTitleLabel}
                    </Typography>
                </Grid>

                <Grid item xs={12}>
                    <Divider className='OperationForm_Content_LinesDivider' />
                </Grid>

                <Grid item xs={12}>
                    <Box className='OperationForm_Content_Lines'>
                        {operation.operationLines.map(
                                (line, index) => (<OperationLineForm className='OperationForm_Content_Lines_LineForm'
                                                                     isValidationActive={isValidationActive} key={index} operationLine={line}
                                                                     onDeleteOperationLine={() => onDeleteOperationLine(index)} />))}

                        <IconButton className='OperationForm_Content_Lines_CreateButton' color='primary' onClick={onCreateOperationLine}>
                            <CreateIcon className='OperationForm_Content_Lines_CreateButton_Icon' />
                        </IconButton>
                    </Box>
                </Grid>
            </Grid>
        </ExpansionPanelDetails>
    </ExpansionPanel>);
}

OperationForm.propTypes = {
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    isValidationActive: PropTypes.bool.isRequired,
    operation: OperationPropType.isRequired,
    onClick: PropTypes.func.isRequired,
    onCreateOperationLine: PropTypes.func.isRequired,
    onDeleteOperation: PropTypes.func.isRequired,
    onDeleteOperationLine: PropTypes.func.isRequired,
};

OperationForm.defaultProps = {
    className: ''
};

export default OperationForm;
