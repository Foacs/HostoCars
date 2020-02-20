import React from 'react';
import { connect } from 'react-redux';
import { Link as RouterLink } from 'react-router-dom';
import PropTypes from 'prop-types';

import { Breadcrumbs as MuiBreadcrumbs, Link, Typography } from '@material-ui/core';
import { HomeRounded as HomeIcon, NavigateNextRounded as NextIcon } from '@material-ui/icons';

import './Breadcrumbs.scss';

/**
 * The breadcrumbs separator.
 */
const separator = <NextIcon fontSize='small' />;

/**
 * The application breadcrumbs component.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {object} currentNavigationPath
 *     The current page navigation path
 * @param {string} currentPageName
 *     The current page name
 *
 * @constructor
 */
function Breadcrumbs({ className, currentNavigationPath, currentPageName }) {
    return (<MuiBreadcrumbs className={className} id='Breadcrumbs' separator={separator}>
        <Link className='HomeLink' component={RouterLink} to='/cars' replace>
            <HomeIcon />
        </Link>

        {currentNavigationPath.map(element => (<Link className='Link' component={RouterLink} key={element.label} to={element.link}>
            <Typography>{element.label}</Typography>
        </Link>))}

        <Typography color='primary' component='div'>{currentPageName}</Typography>
    </MuiBreadcrumbs>);
}

const mapStateToProps = (state) => ({
    currentPageName: state.navigation.currentPageName,
    currentNavigationPath: state.navigation.currentNavigationPath
});

Breadcrumbs.propTypes = {
    className: PropTypes.string,
    currentNavigationPath: PropTypes.arrayOf(PropTypes.shape({
        label: PropTypes.string.isRequired,
        link: PropTypes.string.isRequired
    })).isRequired,
    currentPageName: PropTypes.node.isRequired
};

Breadcrumbs.defaultProps = {
    className: ''
};

export default connect(mapStateToProps, null)(Breadcrumbs);
