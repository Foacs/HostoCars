import React from 'react';
import PropTypes from 'prop-types';

import { Grid, Typography } from '@material-ui/core';
import { ErrorOutlineRounded as ErrorIcon } from '@material-ui/icons';

import StyledErrorPanel from './StyledErrorPanel';

function ErrorPanel({ className, transparent }) {
    const componentClassName = `ErrorPanel ${transparent && 'ErrorPanel_transparent'} ${className}`;

    return (
        <StyledErrorPanel className={componentClassName}>
            <Grid alignItems='center' className='ErrorPanel-Grid' container direction='column'>
                <Grid className='ErrorPanel-Grid-IconItem' item>
                    <ErrorIcon className='ErrorPanel-Grid-IconItem-Icon' />
                </Grid>

                <Grid className='ErrorPanel-Grid-LabelItem' item>
                    <Typography className='ErrorPanel-Grid-LabelItem-Label' variant='h6'>Chargement</Typography>
                </Grid>
            </Grid>
        </StyledErrorPanel>
    );
}

ErrorPanel.propTypes = {
    className: PropTypes.string,
    transparent: PropTypes.bool
};

ErrorPanel.defaultProps = {
    className: '',
    transparent: false
};

export default ErrorPanel;
