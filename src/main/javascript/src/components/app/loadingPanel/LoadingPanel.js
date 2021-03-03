import React from 'react';
import PropTypes from 'prop-types';

import { CircularProgress, Grid, Paper, Typography } from '@material-ui/core';

import './LoadingPanel.scss';

/**
 * The loading panel component.
 *
 * @param {string} [className = '']
 *     The component class name
 *
 * @constructor
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
    className: PropTypes.string
};

LoadingPanel.defaultProps = {
    className: ''
};

export default LoadingPanel;
