import { Breadcrumbs as MuiBreadcrumbs, Link, Typography } from '@material-ui/core';
import { HomeRounded as HomeIcon, NavigateNextRounded as NextIcon } from '@material-ui/icons';
import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';
import { Link as RouterLink } from 'react-router-dom';

import { NavigationPathPropType } from 'resources';

import './Breadcrumbs.scss';

const separator = <NextIcon fontSize='small' className='Separator' />;

function Breadcrumbs({ className, currentNavigationPath, currentPageName }) {
    return <MuiBreadcrumbs className={className} id='Breadcrumbs' separator={separator}>
        <Link className='HomeLink' component={RouterLink} to='/cars'>
            <HomeIcon />
        </Link>

        {currentNavigationPath.map(element => (<Link className='Link' component={RouterLink} key={element.label} to={element.link}>
            <Typography className='Link-Label'>{element.label}</Typography>
        </Link>))}

        <Typography className='CurrentPageLabel'>{currentPageName}</Typography>
    </MuiBreadcrumbs>;
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

export default connect(mapStateToProps, null)(Breadcrumbs);
