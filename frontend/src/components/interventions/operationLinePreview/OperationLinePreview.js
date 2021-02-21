import React from 'react';
import PropTypes from 'prop-types';

import { Checkbox, ListItem, ListItemText } from '@material-ui/core';
import { CheckCircleOutline as CheckedIcon, RadioButtonUnchecked as UncheckedIcon } from '@material-ui/icons';

import { OperationLinePropType } from 'resources';

import './OperationLinePreview.scss';

/**
 * The operation preview component.
 *
 * @param [className = '']
 *     The component class name
 * @param [divider = false]
 *     If the list item has to have a border
 * @param operationLine
 *     The operation line of the preview
 *
 * @constructor
 */
function OperationLinePreview({
    className,
    divider,
    operationLine
}) {
    return (<ListItem className={className} divider={divider} id='OperationLinePreview'>
        <ListItemText className='Description' primary={operationLine.description} primaryTypographyProps={{ variant: 'body2' }}
                      secondary={operationLine.type} secondaryTypographyProps={{ variant: 'caption' }} />

        <Checkbox checked={operationLine.done} checkedIcon={<CheckedIcon className='CheckedIcon' />} className='Done' disabled
                  icon={<UncheckedIcon className='UncheckedIcon' />} />
    </ListItem>);
}

OperationLinePreview.propTypes = {
    className: PropTypes.string,
    divider: PropTypes.bool,
    operationLine: OperationLinePropType.isRequired
};

OperationLinePreview.defaultProps = {
    className: '',
    divider: false
};

export default OperationLinePreview;
