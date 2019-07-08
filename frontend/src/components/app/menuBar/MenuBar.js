import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { List, ListItem, ListItemIcon, ListItemText, Typography } from '@material-ui/core';
import { DirectionsCarRounded as CarsIcon, ListAltRounded as InterventionsIcon, } from '@material-ui/icons';

import StyledMenuBar from './StyledMenuBar';

import { changeSelectedMenuIndexAction } from 'actions';

import { Logo } from 'resources';

const menuItems = [
    { label: 'Voitures', link: '/cars', icon: <CarsIcon /> },
    { label: 'Interventions', link: '/interventions', icon: <InterventionsIcon /> }
];

function MenuBar({ changeSelectedMenuIndex, className, selectedMenuIndex }) {
    const componentClassName = `MenuBar ${className}`;

    const footerText = `${process.env.REACT_APP_NAME} v${process.env.REACT_APP_VERSION}`;

    return (
        <StyledMenuBar anchor='left' className={componentClassName} variant='permanent'>
            <Logo className='MenuBar-Logo' />

            <List className='MenuBar-MenuList'>
                {menuItems.map((menuItem, index) => (
                    <ListItem
                        button
                        className='MenuBar-MenuList-MenuItem'
                        component={Link}
                        key={menuItem.label}
                        onClick={() => changeSelectedMenuIndex(index)}
                        selected={index === selectedMenuIndex}
                        to={menuItem.link}>
                        <ListItemIcon className='MenuBar-MenuList-MenuItem-Icon'>
                            {menuItem.icon}
                        </ListItemIcon>

                        <ListItemText className='MenuBar-MenuList-MenuItem-Label' primary={menuItem.label} />
                    </ListItem>
                ))}
            </List>

            <Typography className='MenuBar-Footer'>
                {footerText}
            </Typography>
        </StyledMenuBar>
    );
}

const mapStateToProps = state => ({
    selectedMenuIndex: state.navigation.selectedMenuIndex
});

const mapDispatchToProps = dispatch => bindActionCreators({
        changeSelectedMenuIndex: changeSelectedMenuIndexAction
    }, dispatch
);

MenuBar.propTypes = {
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    className: PropTypes.string,
    selectedMenuIndex: PropTypes.number.isRequired
};

MenuBar.defaultProps = {
    className: ''
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MenuBar);
