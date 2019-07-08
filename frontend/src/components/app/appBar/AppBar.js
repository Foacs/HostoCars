import React from 'react';
import PropTypes from 'prop-types';

import { Toolbar } from '@material-ui/core';

import { Breadcrumbs } from 'components';

import StyledAppBar from './StyledAppBar';

function AppBar({ className }) {
    const componentClassName = `AppBar ${className}`;

    return (
        <StyledAppBar className={componentClassName} position='fixed'>
            <Toolbar className='AppBar-Toolbar'>
                <Breadcrumbs className='AppBar-Toolbar-Breadcrumbs' />
            </Toolbar>
        </StyledAppBar>
    );
}

AppBar.propTypes = {
    className: PropTypes.string
};

AppBar.defaultProps = {
    className: ''
};

export default AppBar;
