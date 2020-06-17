import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import PropTypes from 'prop-types';

import { Chip, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, Link, Typography } from '@material-ui/core';
import { ExpandMoreRounded as ExpandIcon } from '@material-ui/icons';

import { Gauge, OperationPreview } from 'components';

import { addLeadingZeros, InterventionPropType } from 'resources';

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

    return (<ExpansionPanel className={className} expanded={expanded} id='InterventionPreview' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <Typography align='center' color='secondary' className='Number' noWrap variant='subtitle1'>{interventionNumber}</Typography>

            <Link className='CarLink' component={RouterLink} to={`cars/${intervention.carId}`}>
                <Typography align='center' className='InterventionCarLinkLabel' color='primary' noWrap variant='body2'>{carRegistration}</Typography>
            </Link>

            <Typography className='Description' noWrap variant='body2'>{intervention.description}</Typography>

            <Chip className='Status' color='primary' label={intervention.status} size='small' variant='outlined' />

            <Chip className='OperationChip' color={isFinished ? 'secondary' : 'primary'} label={`${finishedOperations} │ ${totalOperations}`}
                  size='small' variant={isFinished ? 'outlined' : 'default'} />

            <Gauge className='AmountGauge' maxValue={intervention.amount} value={intervention.paidAmount} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='Content'>
            <Grid className='OperationsGrid' container>
                <Grid className='Item' item xs={12}>
                    {intervention.operationList.map((operation, index) => (
                            <OperationPreview expanded={expandedOperationIndex === index} key={index}
                                              onClick={() => setExpandedOperationIndex(expandedOperationIndex === index ? false : index)}
                                              operation={operation} />)
                    )}
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
