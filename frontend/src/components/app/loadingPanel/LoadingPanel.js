import React from 'react';
import PropTypes from 'prop-types';

import { CircularProgress, Grid, Typography } from '@material-ui/core';

import StyledLoadingPanel from './StyledLoadingPanel';

function LoadingPanel({ className, transparent }) {
    const componentClassName = `LoadingPanel ${transparent && 'LoadingPanel_transparent'} ${className}`;

    return (
        <StyledLoadingPanel className={componentClassName}>
            <Grid alignItems='center' className='LoadingPanel-Grid' container direction='column'>
                <Grid className='LoadingPanel-Grid-ProgressItem' item>
                    <CircularProgress className='LoadingPanel-Grid-ProgressItem-Progress' size={80} thickness={2} />
                </Grid>

                <Grid item className='LoadingPanel-Grid-LabelItem'>
                    <Typography className='LoadingPanel-Grid-LabelItem-Label' variant='h6'>Chargement</Typography>
                </Grid>
            </Grid>
        </StyledLoadingPanel>
    );
}

LoadingPanel.propTypes = {
    className: PropTypes.string,
    transparent: PropTypes.bool
};

LoadingPanel.defaultProps = {
    className: '',
    transparent: false
};

export default LoadingPanel;
