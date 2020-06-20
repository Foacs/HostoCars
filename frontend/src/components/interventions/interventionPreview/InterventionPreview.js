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
 * @param carRegistration
 *     The registration number of the intervention's car
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
function InterventionPreview({ carRegistration, className, expanded, intervention, onClick }) {
    const [ expandedOperationIndex, setExpandedOperationIndex ] = useState(false);

    const interventionNumber = `${intervention.creationYear}-${addLeadingZeros(intervention.number, 2)}`;
    const finishedOperations = intervention.operationList.filter(operation => !operation.operationLineList.some(line => !line.done)).length;
    const totalOperations = intervention.operationList.length;
    const isFinished = finishedOperations === totalOperations;

    const currentStatusIndex = INTERVENTION_STATUS_STEPS.indexOf(intervention.status);

    return (<ExpansionPanel className={className} expanded={expanded} id='InterventionPreview' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <Typography align='center' color='secondary' className='Number' noWrap variant='subtitle1'>{interventionNumber}</Typography>

            <Link className='CarLink' component={RouterLink} to={`cars/${intervention.carId}`}>
                <Typography align='center' className='InterventionCarLinkLabel' color='primary' noWrap variant='body2'>{carRegistration}</Typography>
            </Link>

            <Typography className='Description' variant='body2'>{intervention.description}</Typography>

            <Chip className='Status' color='primary' label={intervention.status} size='small' variant='outlined' />

            <Chip className='OperationChip' color={isFinished ? 'secondary' : 'primary'} label={`${finishedOperations} │ ${totalOperations}`}
                  size='small' variant={isFinished ? 'outlined' : 'default'} />

            <Gauge className='AmountGauge' maxValue={intervention.amount} value={intervention.paidAmount} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='Content'>
            <Grid container>
                <Grid container item xs={9}>
                    <Grid alignItems='center' container item xs={12}>
                        <Stepper activeStep={currentStatusIndex} alternativeLabel className='StatusStepper' style={{ width: '100%' }}>
                            {INTERVENTION_STATUS_STEPS.map((label, index) => (
                                    <Step key={index} style={currentStatusIndex === index ? { color: 'red !important' } : {}}>
                                        <StepLabel>{label}</StepLabel>
                                    </Step>
                            ))}
                        </Stepper>
                    </Grid>

                    <Grid alignItems='center' container item xs={12}>
                        <Typography align='center' className='Comments' variant='body'>{intervention.comments}</Typography>
                    </Grid>
                </Grid>

                <Grid container direction='row' item xs={3}>
                    <Grid container item xs={5}>
                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='right' className='MileageLabel' noWrap variant='body'>Kilométrage</Typography>
                        </Grid>

                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='right' className='TimeLabel' noWrap variant='body'>Temps</Typography>
                        </Grid>

                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='right' className='AmountLabel' noWrap variant='body'>Prix</Typography>
                        </Grid>
                    </Grid>

                    <Grid item xs={0}>
                        <Divider orientation='vertical' />
                    </Grid>

                    <Grid container item xs={6}>
                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='center' className='Mileage' noWrap variant='body'>{`${intervention.mileage} km`}</Typography>
                        </Grid>

                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='center' className='Time' noWrap
                                        variant='body'>{`${intervention.realTime} / ${intervention.estimatedTime} h`}</Typography>
                        </Grid>

                        <Grid alignItems='center' container item xs={12}>
                            <Typography align='center' className='Amount' noWrap
                                        variant='body'>{`${intervention.paidAmount} / ${intervention.amount} €`}</Typography>
                        </Grid>
                    </Grid>
                </Grid>

                <Grid alignItems='center' container item xs={12}>
                    <Typography align='center' className='OperationListTitle' noWrap variant='overline'>Opérations</Typography>
                </Grid>

                <Grid item xs={12}>
                    <Divider fullWidth />
                </Grid>

                <Grid item xs={12}>
                    <Box className='OperationList'>
                        {intervention.operationList.map((operation, index) => (
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
    carRegistration: PropTypes.string.isRequired,
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    intervention: InterventionPropType.isRequired,
    onClick: PropTypes.func.isRequired,
};

InterventionPreview.defaultProps = {
    className: ''
};

export default InterventionPreview;
