import React, { useState } from 'react';
import PropTypes from 'prop-types';

import {
    Box, Chip, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, IconButton, Step, StepButton, Stepper, TextField,
    Typography
} from '@material-ui/core';
import { AddCircleOutline as CreateIcon, ExpandMoreRounded as ExpandIcon, RemoveCircleOutline as DeleteIcon } from '@material-ui/icons';

import { Gauge, OperationForm } from 'components';
import { addLeadingZeros, INTERVENTION_STATUS_STEPS, InterventionPropType, stopPropagation } from 'resources';

import './InterventionForm.scss';

// Declares the constants
const amountFieldLabel = 'Dû (€)';
const amountLabel = 'Commentaires';
const commentsFieldLabel = 'Commentaires';
const descriptionFieldLabel = 'Description';
const estimatedTimeFieldLabel = 'Estimé (h)';
const mileageFieldLabel = '(km)';
const mileageLabel = 'Kilométrage';
const operationsTitleLabel = 'Opérations *';
const paidAmountFieldLabel = 'Payé (€)';
const realTimeFieldLabel = 'Réel (h)';
const timeLabel = 'Temps';

/**
 * The intervention form component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
 * @param intervention
 *     The intervention of the form
 * @param isValidationActive
 *     If the validation is active or not
 * @param onClick
 *     The action to trigger when clicking on the panel
 * @param onCreateOperation
 *     The action to trigger when creating an operation
 * @param onCreateOperationLine
 *     The action to trigger when creating an operation line
 * @param onDeleteIntervention
 *     The action to trigger when deleting an intervention
 * @param onDeleteOperation
 *     The action to trigger when deleting an operation
 * @param onDeleteOperationLine
 *     The action to trigger when deleting an operation line
 *
 * @constructor
 */
function InterventionForm({
    className,
    expanded,
    intervention,
    isValidationActive,
    onClick,
    onCreateOperation,
    onCreateOperationLine,
    onDeleteIntervention,
    onDeleteOperation,
    onDeleteOperationLine
}) {
    // Initializes the state
    const [ amount, setAmount ] = useState(intervention.amount);
    const [ comments, setComments ] = useState(intervention.comments);
    const [ description, setDescription ] = useState(intervention.description);
    const [ estimatedTime, setEstimatedTime ] = useState(intervention.estimatedTime);
    const [ expandedOperationIndex, setExpandedOperationIndex ] = useState(-1);
    const [ mileage, setMileage ] = useState(intervention.mileage);
    const [ paidAmount, setPaidAmount ] = useState(intervention.paidAmount);
    const [ realTime, setRealTime ] = useState(intervention.realTime);
    const [ status, setStatus ] = useState(intervention.status);

    // Forces the initialization
    if (amount !== intervention.amount) {
        setAmount(intervention.amount);
    }
    if (comments !== intervention.comments) {
        setComments(intervention.comments);
    }
    if (description !== intervention.description) {
        setDescription(intervention.description);
    }
    if (estimatedTime !== intervention.estimatedTime) {
        setEstimatedTime(intervention.estimatedTime);
    }
    if (mileage !== intervention.mileage) {
        setMileage(intervention.mileage);
    }
    if (paidAmount !== intervention.paidAmount) {
        setPaidAmount(intervention.paidAmount);
    }
    if (realTime !== intervention.realTime) {
        setRealTime(intervention.realTime);
    }
    if (status !== intervention.status) {
        setStatus(intervention.status);
    }

    // Compute internal values
    const interventionNumber = undefined !== intervention.year && undefined !== intervention.number
            ? `${intervention.year}-${addLeadingZeros(intervention.number, 2)}` : '';
    const finishedOperations = intervention.operations.filter(operation => !operation.operationLines.some(line => !line.done)).length;
    const totalOperations = intervention.operations.length;
    const areOperationsFinished = finishedOperations === totalOperations;
    const operationStatusLabel = `${finishedOperations} ⋮ ${totalOperations}`;

    const gaugePaidAmount = paidAmount ? paidAmount : 1;
    const gaugeMaxValue = amount ? amount : gaugePaidAmount;
    const gaugeToPayAmount = paidAmount < gaugeMaxValue ? paidAmount : gaugeMaxValue;
    const gaugeValue = paidAmount ? gaugeToPayAmount : 0;

    const currentStatusIndex = INTERVENTION_STATUS_STEPS.indexOf(status);
    const isInterventionFinished = currentStatusIndex === (INTERVENTION_STATUS_STEPS.length - 1);
    const stepClassName =
            `InterventionForm_Content_StatusStepper_Step${isInterventionFinished && ' InterventionForm_Content_StatusStepper_Step-finished'}`;

    const isDescriptionInError = isValidationActive && (null === description || undefined === description || '' === description);
    const areOperationsInError = isValidationActive && (null === intervention.operations || undefined === intervention.operations
            || 0 === intervention.operations.length);

    /**
     * Handles the amount change action.
     *
     * @param {object} e
     *     The event
     */
    const onAmountChange = (e) => {
        setAmount(e.target.value);
        intervention.amount = e.target.value;
    };

    /**
     * Handles the comments change action.
     *
     * @param {object} e
     *     The event
     */
    const onCommentsChange = (e) => {
        setComments(e.target.value);
        intervention.comments = e.target.value;
    };

    /**
     * Handles the delete button click action.
     *
     * @param {object} e
     *     The event
     */
    const onDeleteButtonClick = (e) => {
        onDeleteIntervention();
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
        intervention.description = e.target.value;
    };

    /**
     * Handles the estimated time change action.
     *
     * @param {object} e
     *     The event
     */
    const onEstimatedTimeChange = (e) => {
        setEstimatedTime(e.target.value);
        intervention.estimatedTime = e.target.value;
    };

    /**
     * Handles the mileage change action.
     *
     * @param {object} e
     *     The event
     */
    const onMileageChange = (e) => {
        setMileage(e.target.value);
        intervention.mileage = e.target.value;
    };

    /**
     * Handles the paid amount change action.
     *
     * @param {object} e
     *     The event
     */
    const onPaidAmountChange = (e) => {
        setPaidAmount(e.target.value);
        intervention.paidAmount = e.target.value;
    };

    /**
     * Handles the real time change action.
     *
     * @param {object} e
     *     The event
     */
    const onRealTimeChange = (e) => {
        setRealTime(e.target.value);
        intervention.realTime = e.target.value;
    };

    /**
     * Handles the status change action.
     *
     * @param {string} s
     *     The status
     */
    const onStatusChange = (s) => {
        setStatus(s);
        intervention.status = s;
    };

    return (<ExpansionPanel className={className} expanded={expanded} id='InterventionForm' onChange={onClick}>
        <ExpansionPanelSummary className='InterventionForm_Summary' expandIcon={<ExpandIcon className='InterventionForm_Summary_ExpandIcon' />}>
            <IconButton className='InterventionForm_Summary_DeleteButton' color='primary' onClick={onDeleteButtonClick}>
                <DeleteIcon />
            </IconButton>

            <Typography align='center' color='secondary' className='InterventionForm_Summary_NumberLabel' noWrap variant='subtitle1'>
                {interventionNumber}
            </Typography>

            <TextField className='InterventionForm_Summary_DescriptionField' error={isDescriptionInError} InputLabelProps={{ shrink: true }}
                       label={descriptionFieldLabel} onChange={onDescriptionChange} onClick={stopPropagation} required value={description}
                       variant='outlined' />

            <Chip className='InterventionForm_Summary_Status' color={isInterventionFinished ? 'secondary' : 'primary'} label={intervention.status}
                  size='small' variant='outlined' />

            <Chip className='InterventionForm_Summary_OperationsStatus' color={areOperationsFinished ? 'secondary' : 'primary'}
                  label={operationStatusLabel} size='small' variant={areOperationsFinished ? 'outlined' : 'default'} />

            <Gauge className='InterventionForm_Summary_AmountGauge' maxValue={gaugeMaxValue} value={gaugeValue} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='InterventionForm_Content'>
            <Grid container>
                <Grid container item xs={9}>
                    <Grid item xs={12}>
                        <Stepper activeStep={currentStatusIndex} alternativeLabel className='InterventionForm_Content_StatusStepper' nonLinear>
                            {INTERVENTION_STATUS_STEPS.map((label, index) => (
                                    <Step className={stepClassName} completed={currentStatusIndex > index || isInterventionFinished} key={index}>
                                        <StepButton className='InterventionForm_Content_StatusStepper_Step_Button'
                                                    onClick={() => onStatusChange(INTERVENTION_STATUS_STEPS[index])}>
                                            {label}
                                        </StepButton>
                                    </Step>
                            ))}
                        </Stepper>
                    </Grid>

                    <Grid item xs={12}>
                        <TextField className='InterventionForm_Content_Comments' InputLabelProps={{ shrink: true }} label={commentsFieldLabel}
                                   onChange={onCommentsChange} value={comments} variant='outlined' />
                    </Grid>
                </Grid>

                <Grid container direction='row' item xs={3}>
                    <Grid container item xs={5}>
                        <Grid item xs={12}>
                            <Typography align='right' className='InterventionForm_Content_MileageLabel' noWrap variant='body2'>
                                {mileageLabel}
                            </Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='right' className='InterventionForm_Content_TimeLabel' noWrap variant='body2'>{timeLabel}</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='right' className='InterventionForm_Content_AmountLabel' noWrap
                                        variant='body2'>{amountLabel}</Typography>
                        </Grid>
                    </Grid>

                    <Grid item>
                        <Divider className='InterventionForm_Content_DataDivider' orientation='vertical' />
                    </Grid>

                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <TextField className='InterventionForm_Content_Mileage' InputLabelProps={{ shrink: true }} label={mileageFieldLabel}
                                       onChange={onMileageChange} value={mileage} variant='outlined' />
                        </Grid>

                        <Grid container item xs={12}>
                            <Grid item xs={6}>
                                <TextField className='InterventionForm_Content_RealTime' InputLabelProps={{ shrink: true }} label={realTimeFieldLabel}
                                           onChange={onRealTimeChange} value={realTime} variant='outlined' />
                            </Grid>

                            <Grid item xs={6}>
                                <TextField className='InterventionForm_Content_EstimatedTime' InputLabelProps={{ shrink: true }}
                                           label={estimatedTimeFieldLabel} onChange={onEstimatedTimeChange} value={estimatedTime} variant='outlined'
                                />
                            </Grid>
                        </Grid>

                        <Grid container item xs={12}>
                            <Grid item xs={6}>
                                <TextField className='InterventionForm_Content_PaidAmount' InputLabelProps={{ shrink: true }}
                                           label={paidAmountFieldLabel} onChange={onPaidAmountChange} value={paidAmount} variant='outlined' />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField className='InterventionForm_Content_Amount' InputLabelProps={{ shrink: true }} label={amountFieldLabel}
                                           onChange={onAmountChange} value={amount} variant='outlined' />
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>

                <Grid item xs={12}>
                    <Typography align='center' className='InterventionForm_Content_OperationsTitle' color={areOperationsInError ? 'error' : 'initial'}
                                noWrap variant='overline'>
                        {operationsTitleLabel}
                    </Typography>
                </Grid>

                <Grid item xs={12}>
                    <Divider className='InterventionForm_Content_OperationsDivider' />
                </Grid>

                <Grid item xs={12}>
                    <Box className='InterventionForm_Content_Operations'>
                        {intervention.operations.map((operation, index) => (
                                <OperationForm className='InterventionForm_Content_Operations_OperationForm'
                                               expanded={expandedOperationIndex === index} isValidationActive={isValidationActive} key={index}
                                               onClick={() => setExpandedOperationIndex(expandedOperationIndex === index ? false : index)}
                                               onCreateOperationLine={() => onCreateOperationLine(index)}
                                               onDeleteOperation={() => onDeleteOperation(index)}
                                               onDeleteOperationLine={i => onDeleteOperationLine(index, i)}
                                               operation={operation} />)
                        )}

                        <IconButton className='InterventionForm_Content_Operations_CreateButton' color='primary' onClick={onCreateOperation}>
                            <CreateIcon className='InterventionForm_Content_Operations_CreateButton_Icon' />
                        </IconButton>
                    </Box>
                </Grid>
            </Grid>
        </ExpansionPanelDetails>
    </ExpansionPanel>);
}

InterventionForm.propTypes = {
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    intervention: InterventionPropType.isRequired,
    isValidationActive: PropTypes.bool.isRequired,
    onClick: PropTypes.func.isRequired,
    onCreateOperation: PropTypes.func.isRequired,
    onCreateOperationLine: PropTypes.func.isRequired,
    onDeleteIntervention: PropTypes.func.isRequired,
    onDeleteOperation: PropTypes.func.isRequired,
    onDeleteOperationLine: PropTypes.func.isRequired
};

InterventionForm.defaultProps = {
    className: ''
};

export default InterventionForm;
