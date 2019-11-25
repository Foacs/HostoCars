import React from 'react';
import PropTypes from 'prop-types';

import { CircularProgress, Grid, Paper, Typography } from '@material-ui/core';

import './LoadingPanel.scss';

/**
 * Loading panel component.
 *
 * @param className
 *     The component class name
 */
function LoadingPanel({ className }) {
    return (<Paper className={className} id='LoadingPanel'>
        <Grid alignItems='center' container direction='column'>
            <Grid item>
                <CircularProgress size={80} thickness={2} />
            </Grid>

            <Grid item>
                <Typography className='Label' color='primary' variant='h6'>Chargement</Typography>
            </Grid>
        </Grid>
    </Paper>);
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
