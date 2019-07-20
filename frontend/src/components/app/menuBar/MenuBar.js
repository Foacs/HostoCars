import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Drawer, List, ListItem, ListItemIcon, ListItemText, Typography } from '@material-ui/core';
import { DirectionsCarRounded as CarsIcon, ListAltRounded as InterventionsIcon, } from '@material-ui/icons';

import { changeSelectedMenuIndexAction } from 'actions';

import { Logo } from 'resources';

import './MenuBar.scss';

const menuItems = [
    { label: 'Voitures', link: '/cars', icon: <CarsIcon /> },
    { label: 'Interventions', link: '/interventions', icon: <InterventionsIcon /> }
];

function MenuBar({ changeSelectedMenuIndex, className, selectedMenuIndex }) {
    const footerText = `${process.env.REACT_APP_NAME} v${process.env.REACT_APP_VERSION}`;

    return (
        <Drawer anchor='left' className={className} id='MenuBar' variant='permanent'>
            <Logo className='Logo' />

            <List className='MenuList'>
                {menuItems.map((menuItem, index) => (
                    <ListItem
                        button
                        className='MenuList-MenuItem'
                        component={Link}
                        key={menuItem.label}
                        onClick={() => changeSelectedMenuIndex(index)}
                        selected={index === selectedMenuIndex}
                        to={menuItem.link}>
                        <ListItemIcon className='MenuList-MenuItem-Icon'>
                            {menuItem.icon}
                        </ListItemIcon>

                        <ListItemText className='MenuList-MenuItem-Label' primary={menuItem.label} />
                    </ListItem>
                ))}
            </List>

            <Typography className='Footer non-selectable'>
                {footerText}
            </Typography>
        </Drawer>
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
