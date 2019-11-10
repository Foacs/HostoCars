import React from 'react';
import { connect } from 'react-redux';
import { Link as RouterLink } from 'react-router-dom';
import PropTypes from 'prop-types';

import { Breadcrumbs as MuiBreadcrumbs, Link, Typography } from '@material-ui/core';
import { HomeRounded as HomeIcon, NavigateNextRounded as NextIcon } from '@material-ui/icons';

import { NavigationPathPropType } from 'resources';

import './Breadcrumbs.scss';

/**
 * The breadcrumbs separator.
 */
const separator = <NextIcon fontSize='small' />;

/**
 * The application breadcrumbs.
 *
 * @param className
 *     The component class name
 * @param currentNavigationPath
 *     The current page navigation path
 * @param currentPageName
 *     The current page name
 */
function Breadcrumbs({ className, currentNavigationPath, currentPageName }) {
    return (<MuiBreadcrumbs className={className} id='Breadcrumbs' separator={separator}>
        <Link className='HomeLink' component={RouterLink} to='/cars'>
            <HomeIcon />
        </Link>

        {currentNavigationPath.map(element => (<Link className='Link' component={RouterLink} key={element.label} to={element.link}>
            <Typography>{element.label}</Typography>
        </Link>))}

        <Typography color='primary' component='div'>{currentPageName}</Typography>
    </MuiBreadcrumbs>);
}

const mapStateToProps = state => ({
    currentPageName: state.navigation.currentPageName,
    currentNavigationPath: state.navigation.currentNavigationPath
});

Breadcrumbs.propTypes = {
    className: PropTypes.string,
    currentNavigationPath: NavigationPathPropType.isRequired,
    currentPageName: PropTypes.node.isRequired
};

Breadcrumbs.defaultProps = {
    className: ''
};

export default connect(mapStateToProps, null)(Breadcrumbs);
