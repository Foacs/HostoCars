import React, { useState } from 'react';
import PropTypes from 'prop-types';

import {
    Box, Chip, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, IconButton, Step, StepButton, Stepper, TextField,
    Typography
} from '@material-ui/core';
import { AddCircleOutline as CreateIcon, ExpandMoreRounded as ExpandIcon, RemoveCircleOutline as DeleteIcon } from '@material-ui/icons';

import { Gauge, OperationForm } from 'components';

import { addLeadingZeros, INTERVENTION_STATUS_STEPS, InterventionPropType } from 'resources';

import './InterventionForm.scss';

/**
 * The intervention form component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
 * @param intervention
 *     The intervention of the form
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
function InterventionForm({ className, expanded, intervention, onClick, onCreateOperation, onCreateOperationLine, onDeleteIntervention, onDeleteOperation, onDeleteOperationLine }) {
    // Initializes the help flag
    const [ expandedOperationIndex, setExpandedOperationIndex ] = useState(-1);

    // Initializes the intervention fields
    const [ description, setDescription ] = useState(intervention.description);
    const [ status, setStatus ] = useState(intervention.status);
    const [ amount, setAmount ] = useState(intervention.amount);
    const [ paidAmount, setPaidAmount ] = useState(intervention.paidAmount);
    const [ mileage, setMileage ] = useState(intervention.mileage);
    const [ realTime, setRealTime ] = useState(intervention.realTime);
    const [ estimatedTime, setEstimatedTime ] = useState(intervention.estimatedTime);
    const [ comments, setComments ] = useState(intervention.comments);

    // Initializes the constraints
    const [ descriptionRequired, setDescriptionRequired ] = React.useState(false);

    const interventionNumber = undefined !== intervention.year && undefined !== intervention.number
            ? `${intervention.year}-${addLeadingZeros(intervention.number, 2)}` : '';
    const finishedOperations = intervention.operations.filter(operation => !operation.operationLines.some(line => !line.done)).length;
    const totalOperations = intervention.operations.length;
    const areOperationsFinished = finishedOperations === totalOperations;

    const gaugeMaxValue = amount ? amount : paidAmount ? paidAmount : 1;
    const gaugeValue = paidAmount ? paidAmount < gaugeMaxValue ? paidAmount : gaugeMaxValue : 0;

    const currentStatusIndex = INTERVENTION_STATUS_STEPS.indexOf(status);
    const isInterventionFinished = currentStatusIndex === (INTERVENTION_STATUS_STEPS.length - 1);
    const stepClassName = isInterventionFinished ? 'Step Step_finished' : 'Step';

    /**
     * Handles the value changed event for the given field.
     *
     * @param {object} e
     *     The event
     * @param {string} field
     *     The field name
     */
    const onFieldValueChanged = (e, field) => {
        switch (field) {
            case 'description':
                setDescription(e.target.value);
                setDescriptionRequired(false);
                intervention.description = e.target.value;
                break;
            case 'status':
                setStatus(e);
                intervention.status = e;
                break;
            case 'amount':
                setAmount(e.target.value);
                intervention.amount = e.target.value;
                break;
            case 'paidAmount':
                setPaidAmount(e.target.value);
                intervention.paidAmount = e.target.value;
                break;
            case 'mileage':
                setMileage(e.target.value);
                intervention.mileage = e.target.value;
                break;
            case 'realTime':
                setRealTime(e.target.value);
                intervention.realTime = e.target.value;
                break;
            case 'estimatedTime':
                setEstimatedTime(e.target.value);
                intervention.estimatedTime = e.target.value;
                break;
            case 'comments':
                setComments(e.target.value);
                intervention.comments = e.target.value;
                break;
            default:
                console.error('Unknown field updated');
        }
    };

    return (<ExpansionPanel className={className} expanded={expanded} id='InterventionForm' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <IconButton className='DeleteButton' color='primary' onClick={e => {
                onDeleteIntervention();
                e.stopPropagation();
            }}>
                <DeleteIcon />
            </IconButton>

            <Typography align='center' color='secondary' className='Number' noWrap variant='subtitle1'>{interventionNumber}</Typography>

            <TextField className='Description' label='Description' onChange={e => onFieldValueChanged(e, 'description')}
                       onClick={e => e.stopPropagation()} required value={description} variant='outlined' />

            <Chip className={`Status ${!intervention.carRegistration && 'Status_withPadding'}`}
                  color={isInterventionFinished ? 'secondary' : 'primary'} label={intervention.status} size='small' variant='outlined' />

            <Chip className='OperationChip' color={areOperationsFinished ? 'secondary' : 'primary'}
                  label={`${finishedOperations} ⋮ ${totalOperations}`}
                  size='small' variant={areOperationsFinished ? 'outlined' : 'default'} />

            <Gauge className='AmountGauge' maxValue={gaugeMaxValue} value={gaugeValue} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='Content'>
            <Grid container>
                <Grid container item xs={9}>
                    <Grid item xs={12}>
                        <Stepper activeStep={currentStatusIndex} alternativeLabel className='StatusStepper' nonLinear>
                            {INTERVENTION_STATUS_STEPS.map((label, index) => (
                                    <Step className={stepClassName} completed={currentStatusIndex > index || isInterventionFinished} key={index}>
                                        <StepButton className='StatusStepButton'
                                                    onClick={() => onFieldValueChanged(INTERVENTION_STATUS_STEPS[index], 'status')}>
                                            {label}
                                        </StepButton>
                                    </Step>
                            ))}
                        </Stepper>
                    </Grid>

                    <Grid item xs={12}>
                        <TextField className='Comments' label='Commentaires' onChange={e => onFieldValueChanged(e, 'comments')}
                                   value={comments} variant='outlined' />
                    </Grid>
                </Grid>

                <Grid container direction='row' item xs={3}>
                    <Grid container item xs={5}>
                        <Grid item xs={12}>
                            <Typography align='right' className='MileageLabel' noWrap variant='body2'>Kilométrage</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='right' className='TimeLabel' noWrap variant='body2'>Temps</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='right' className='AmountLabel' noWrap variant='body2'>Prix</Typography>
                        </Grid>
                    </Grid>

                    <Grid item>
                        <Divider className='Divider' orientation='vertical' />
                    </Grid>

                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <TextField className='Mileage' InputLabelProps={{ shrink: true }} label='(km)'
                                       onChange={e => onFieldValueChanged(e, 'mileage')} value={mileage} variant='outlined' />
                        </Grid>

                        <Grid container item xs={12}>
                            <Grid item xs={6}>
                                <TextField className='RealTime' InputLabelProps={{ shrink: true }} label='Réel (h)'
                                           onChange={e => onFieldValueChanged(e, 'realTime')} value={realTime} variant='outlined' />
                            </Grid>

                            <Grid item xs={6}>
                                <TextField className='EstimatedTime' InputLabelProps={{ shrink: true }} label='Estimé (h)'
                                           onChange={e => onFieldValueChanged(e, 'estimatedTime')} value={estimatedTime} variant='outlined' />
                            </Grid>
                        </Grid>

                        <Grid container item xs={12}>
                            <Grid item xs={6}>
                                <TextField className='PaidAmount' InputLabelProps={{ shrink: true }} label='Payé (€)'
                                           onChange={e => onFieldValueChanged(e, 'paidAmount')} value={paidAmount} variant='outlined' />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField className='Amount' InputLabelProps={{ shrink: true }} label='Dû (€)'
                                           onChange={e => onFieldValueChanged(e, 'amount')} value={amount} variant='outlined' />
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>

                <Grid item xs={12}>
                    <Typography align='center' className='OperationListTitle' noWrap variant='overline'>Opérations</Typography>
                </Grid>

                <Grid item xs={12}>
                    <Divider />
                </Grid>

                <Grid item xs={12}>
                    <Box className='OperationList'>
                        {intervention.operations.map((operation, index) => (
                                <OperationForm expanded={expandedOperationIndex === index} key={index}
                                               onClick={() => setExpandedOperationIndex(expandedOperationIndex === index ? false : index)}
                                               onCreateOperationLine={() => onCreateOperationLine(index)}
                                               onDeleteOperation={() => onDeleteOperation(index)}
                                               onDeleteOperationLine={i => onDeleteOperationLine(index, i)}
                                               operation={operation} />)
                        )}

                        <ExpansionPanel className='AddOperationListItem' expanded={true}>
                            <ExpansionPanelSummary>
                                <IconButton className='AddOperationButton' color='primary' onClick={onCreateOperation}>
                                    <CreateIcon />
                                </IconButton>
                            </ExpansionPanelSummary>
                        </ExpansionPanel>
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
    onClick: PropTypes.func.isRequired,
};

InterventionForm.defaultProps = {
    className: ''
};

export default InterventionForm;
