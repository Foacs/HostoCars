import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Chip, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, IconButton, List, TextField } from '@material-ui/core';
import { ExpandMoreRounded as ExpandIcon, RemoveCircleOutline as DeleteIcon } from '@material-ui/icons';

import { OperationLineForm } from 'components';
import { OperationPropType } from 'resources';

import './OperationForm.scss';

/**
 * The operation form component.
 *
 * @param [className = '']
 *     The component class name
 * @param expanded
 *     If the expansion panel is expanded or not
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
function OperationForm({ className, expanded, operation, onClick, onCreateOperationLine, onDeleteOperation, onDeleteOperationLine }) {
    // Initializes the operation fields
    const [ label, setLabel ] = useState(operation.label);

    const finishedLines = operation.operationLines.filter(line => line.done).length;
    const totalLines = operation.operationLines.length;
    const isFinished = finishedLines === totalLines;

    return (<ExpansionPanel className={className} elevation={0} expanded={expanded} id='OperationForm' onChange={onClick}>
        <ExpansionPanelSummary className='Header' expandIcon={<ExpandIcon className='ExpandIcon' />}>
            <IconButton className='DeleteButton' color='primary' onClick={e => {
                onDeleteOperation();
                e.stopPropagation();
            }}>
                <DeleteIcon />
            </IconButton>

            <TextField className='Label' onClick={e => e.stopPropagation()} onChange={e => setLabel(e.target.value)} value={label}
                       variant='outlined' />

            <Chip className='LinesChip' color={isFinished ? 'secondary' : 'primary'} label={`${finishedLines} ⋮ ${totalLines}`} size='small'
                  variant={isFinished ? 'outlined' : 'default'} />
        </ExpansionPanelSummary>

        <ExpansionPanelDetails className='Content'>
            <List className='List'>
                {operation.operationLines.map(
                        (line, index) => (<OperationLineForm divider={totalLines - 1 !== index} key={index} operationLine={line} />))}
            </List>
        </ExpansionPanelDetails>
    </ExpansionPanel>);
}

OperationForm.propTypes = {
    className: PropTypes.string,
    expanded: PropTypes.bool.isRequired,
    operation: OperationPropType.isRequired,
    onClick: PropTypes.func.isRequired,
};

OperationForm.defaultProps = {
    className: ''
};

export default OperationForm;
