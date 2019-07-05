import React from 'react';
import { connect } from 'react-redux';
import { Link as RouterLink } from 'react-router-dom';

import StyledBreadcrumbs from './StyledBreadcrumbs';

import { Link, Typography } from '@material-ui/core';
import { HomeRounded as HomeIcon, NavigateNextRounded as NextIcon } from '@material-ui/icons';

function Breadcrumbs({ currentNavigationPath, currentPageName }) {
    return (
        <StyledBreadcrumbs separator={<NextIcon fontSize="small" />}>
            <Link component={RouterLink} to="/cars">
                <HomeIcon />
            </Link>

            {currentNavigationPath.map(element => (
                <Link component={RouterLink} key={element.label} to={element.link}>
                    {element.label}
                </Link>
            ))}

            <Typography>{currentPageName}</Typography>
        </StyledBreadcrumbs>
    );
}

const mapStateToProps = state => ({
    currentPageName: state.navigation.currentPageName,
    currentNavigationPath: state.navigation.currentNavigationPath
});

export default connect(
    mapStateToProps,
    null
)(Breadcrumbs);
