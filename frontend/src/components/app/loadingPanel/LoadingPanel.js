import { CircularProgress, Grid, Paper, Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';

import './LoadingPanel.scss';

function LoadingPanel({ className, transparent }) {
    const componentClassName = `${className} ${transparent && 'transparent'}`;

    return <Paper className={componentClassName} id='LoadingPanel'>
        <Grid alignItems='center' className='Grid' container direction='column'>
            <Grid className='Grid-ProgressItem' item>
                <CircularProgress className='Grid-ProgressItem-Progress' size={80} thickness={2} />
            </Grid>

            <Grid item className='Grid-LabelItem'>
                <Typography className='Grid-LabelItem-Label' variant='h6'>Chargement</Typography>
            </Grid>
        </Grid>
    </Paper>;
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
