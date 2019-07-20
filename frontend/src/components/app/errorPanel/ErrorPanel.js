import React from 'react';
import PropTypes from 'prop-types';

import { Grid, Paper, Typography } from '@material-ui/core';
import { ErrorOutlineRounded as ErrorIcon } from '@material-ui/icons';

import './ErrorPanel.scss';

function ErrorPanel({ className, transparent }) {
    const componentClassName = `${transparent && 'transparent'} ${className}`;

    return (
        <Paper className={componentClassName} id='ErrorPanel'>
            <Grid alignItems='center' className='Grid' container direction='column'>
                <Grid className='Grid-IconItem' item>
                    <ErrorIcon className='Grid-IconItem-Icon' />
                </Grid>

                <Grid className='Grid-LabelItem' item>
                    <Typography className='Grid-LabelItem-Label' variant='h6'>Erreur</Typography>
                </Grid>
            </Grid>
        </Paper>
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
