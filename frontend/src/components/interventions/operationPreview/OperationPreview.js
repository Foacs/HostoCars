import React from 'react';
import PropTypes from 'prop-types';

import { Chip, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, List, Typography } from '@material-ui/core';
import { ExpandMoreRounded as ExpandIcon } from '@material-ui/icons';

import { OperationLinePreview } from 'components';
import { OperationPropType } from 'resources';

import './OperationPreview.scss';

/**
 * The operation preview component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
 * @param operation
 *     The operation of the preview
 * @param onClick
 *     The action to trigger when clicking on the panel
 *
 * @constructor
 */
function OperationPreview({
    className,
    expanded,
    operation,
    onClick
}) {
    const finishedLines = operation.operationLines.filter(line => line.done).length;
    const totalLines = operation.operationLines.length;
    const isFinished = finishedLines === totalLines;

    return (<ExpansionPanel className={className} elevation={0} expanded={expanded} id='OperationPreview' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <Typography className='Label' noWrap variant='body2'>{operation.label}</Typography>

            <Chip className='LinesChip' color={isFinished ? 'secondary' : 'primary'} label={`${finishedLines} ⋮ ${totalLines}`} size='small'
                  variant={isFinished ? 'outlined' : 'default'} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='Content'>
            <List className='List'>
                {operation.operationLines.map(
                        (line, index) => (<OperationLinePreview divider={totalLines - 1 !== index} key={index} operationLine={line} />))}
            </List>
        </ExpansionPanelDetails>
    </ExpansionPanel>);
}

OperationPreview.propTypes = {
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    operation: OperationPropType.isRequired,
    onClick: PropTypes.func.isRequired,
};

OperationPreview.defaultProps = {
    className: ''
};

export default OperationPreview;
