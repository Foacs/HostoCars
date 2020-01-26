import React from 'react';
import PropTypes from 'prop-types';

import { Grid, Paper, Typography } from '@material-ui/core';
import { ErrorOutlineRounded as ErrorIcon } from '@material-ui/icons';

import './ErrorPanel.scss';

/**
 * The error panel component.
 *
 * @param {string} [className = '']
 *     The component class name
 *
 * @constructor
 */
function ErrorPanel({ className }) {
    return (<Paper className={className} id='ErrorPanel'>
        <Grid alignItems='center' container direction='column'>
            <Grid item>
                <ErrorIcon className='Icon' color='error' />
            </Grid>

            <Grid item>
                <Typography className='Label' color='error' variant='h6'>Erreur</Typography>
            </Grid>
        </Grid>
    </Paper>);
}

ErrorPanel.propTypes = {
    className: PropTypes.string
};

ErrorPanel.defaultProps = {
    className: ''
};

export default ErrorPanel;
