import React from 'react';
import PropTypes from 'prop-types';

import { AppBar as MaterialAppBar, Toolbar } from '@material-ui/core';

import { Breadcrumbs } from 'components';

import './AppBar.scss';

/**
 * The application's bar component.
 *
 * @param className
 *     The component class name
 */
function AppBar({ className }) {
    return (<MaterialAppBar className={className} id='AppBar' position='fixed'>
        <Toolbar>
            <Breadcrumbs />
        </Toolbar>
    </MaterialAppBar>);
}

AppBar.propTypes = {
    className: PropTypes.string
};

AppBar.defaultProps = {
    className: ''
};

export default AppBar;
