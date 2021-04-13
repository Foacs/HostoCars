import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import PropTypes from 'prop-types';

import {
    Box, Chip, Divider, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, Link, Step, StepLabel, Stepper, Typography
} from '@material-ui/core';
import { ExpandMoreRounded as ExpandIcon } from '@material-ui/icons';

import { Gauge, OperationPreview } from 'components';

import { addLeadingZeros, INTERVENTION_STATUS_STEPS, InterventionPropType } from 'resources';

import './InterventionPreview.scss';

/**
 * The intervention preview component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
 * @param intervention
 *     The intervention of the preview
 * @param onClick
 *     The action to trigger when clicking on the panel
 *
 * @constructor
 */
function InterventionPreview({
    className,
    expanded,
    intervention,
    onClick
}) {
    const [ expandedOperationIndex, setExpandedOperationIndex ] = useState(false);

    const interventionNumber = `${intervention.year}-${addLeadingZeros(intervention.number, 2)}`;
    const finishedOperations = intervention.operations.filter(operation => !operation.operationLines.some(line => !line.done)).length;
    const totalOperations = intervention.operations.length;
    const areOperationsFinished = finishedOperations === totalOperations;

    const gaugePaidAmount = intervention.paidAmount ? intervention.paidAmount : 1;
    const gaugeMaxValue = intervention.amount ? intervention.amount : gaugePaidAmount;
    const gaugeToPayAmount = intervention.paidAmount < gaugeMaxValue ? intervention.paidAmount : gaugeMaxValue;
    const gaugeValue = intervention.paidAmount ? gaugeToPayAmount : 0;

    const currentStatusIndex = INTERVENTION_STATUS_STEPS.indexOf(intervention.status);
    const isInterventionFinished = currentStatusIndex === (INTERVENTION_STATUS_STEPS.length - 1);
    const stepClassName = isInterventionFinished ? 'Step Step_finished' : 'Step';

    const mileageValue = intervention.mileage ? `${intervention.mileage} km` : '';
    const realTimeValue = intervention.realTime ? intervention.realTime : '-';
    const estimatedTimeValue = intervention.estimatedTime ? intervention.estimatedTime : '-';
    const timeValue = intervention.realTime || intervention.estimatedTime ? `${realTimeValue} / ${estimatedTimeValue} h` : '';

    const paidAmountValue = intervention.paidAmount ? intervention.paidAmount : 0;
    const totalAmountValue = intervention.amount ? intervention.amount : '-';
    const amountValue = intervention.paidAmount || intervention.amount ? `${paidAmountValue} / ${totalAmountValue} €` : '';

    return (<ExpansionPanel className={`${className} ${!intervention.carRegistration && 'InterventionPreview_withoutShadow'}`} expanded={expanded}
                            id='InterventionPreview' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <Typography align='center' color='secondary' className='Number' noWrap variant='subtitle1'>{interventionNumber}</Typography>

            {intervention.carRegistration && <Link className='CarLink' component={RouterLink} to={`cars/${intervention.carId}`}>
                <Typography align='center' className='InterventionCarLinkLabel' color='primary' noWrap variant='body2'>
                    {intervention.carRegistration}
                </Typography>
            </Link>}

            <Typography className='Description' variant='body2'>{intervention.description}</Typography>

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
                        <Stepper activeStep={currentStatusIndex} alternativeLabel className='StatusStepper'>
                            {INTERVENTION_STATUS_STEPS.map((label, index) => (
                                    <Step className={stepClassName} completed={currentStatusIndex > index || isInterventionFinished} key={index}>
                                        <StepLabel>{label}</StepLabel>
                                    </Step>
                            ))}
                        </Stepper>
                    </Grid>

                    <Grid item xs={12}>
                        <Typography align='center' className='Comments' variant='body2'>{intervention.comments}</Typography>
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
                        <Divider orientation='vertical' />
                    </Grid>

                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <Typography align='center' className='Mileage' noWrap variant='body2'>{mileageValue}</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='center' className='Time' noWrap variant='body2'>{timeValue}</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography align='center' className='Amount' noWrap variant='body2'>{amountValue}</Typography>
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
                                <OperationPreview expanded={expandedOperationIndex === index} key={index}
                                                  onClick={() => setExpandedOperationIndex(expandedOperationIndex === index ? false : index)}
                                                  operation={operation} />)
                        )}
                    </Box>
                </Grid>
            </Grid>
        </ExpansionPanelDetails>
    </ExpansionPanel>);
}

InterventionPreview.propTypes = {
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    intervention: InterventionPropType.isRequired,
    onClick: PropTypes.func.isRequired,
};

InterventionPreview.defaultProps = {
    className: ''
};

export default InterventionPreview;
