import React from 'react';
import { connect } from 'react-redux';
import { Link as RouterLink } from 'react-router-dom';
import PropTypes from 'prop-types';

import { Link, Typography } from '@material-ui/core';
import { HomeRounded as HomeIcon, NavigateNextRounded as NextIcon } from '@material-ui/icons';

import StyledBreadcrumbs from './StyledBreadcrumbs';

import { NavigationPathPropType } from 'resources';

const separator = <NextIcon fontSize='small' className='Breadcrumb-Separator' />;

function Breadcrumbs({ className, currentNavigationPath, currentPageName }) {
    const componentClassName = `Breadcrumb ${className}`;

    return (
        <StyledBreadcrumbs className={componentClassName} separator={separator}>
            <Link className='Breadcrumb-HomeLink' component={RouterLink} to='/cars'>
                <HomeIcon />
            </Link>

            {currentNavigationPath.map(element => (
                <Link className='Breadcrumb-Link' component={RouterLink} key={element.label} to={element.link}>
                    <Typography className='Breadcrumb-Link-Label'>{element.label}</Typography>
                </Link>
            ))}

            <Typography className='Breadcrumb-CurrentPageLabel'>{currentPageName}</Typography>
        </StyledBreadcrumbs>
    );
}

const mapStateToProps = state => ({
    currentPageName: state.navigation.currentPageName,
    currentNavigationPath: state.navigation.currentNavigationPath
});

Breadcrumbs.propTypes = {
    className: PropTypes.string,
    currentNavigationPath: NavigationPathPropType.isRequired,
    currentPageName: PropTypes.string.isRequired
};

Breadcrumbs.defaultProps = {
    className: ''
};

export default connect(
    mapStateToProps,
    null
)(Breadcrumbs);
