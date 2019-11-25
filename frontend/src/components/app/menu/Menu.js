import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Drawer, List, ListItem, ListItemIcon, ListItemText, Typography } from '@material-ui/core';
import { DirectionsCarRounded as CarsIcon, ListAltRounded as InterventionsIcon } from '@material-ui/icons';

import { changeSelectedMenuIndexAction } from 'actions';
import { Logo } from 'resources';

import './Menu.scss';

/**
 * Defines the menu items.
 */
const menuItems = [ {
    label: 'Voitures',
    link: '/cars',
    icon: <CarsIcon />
}, {
    label: 'Interventions',
    link: '/interventions',
    icon: <InterventionsIcon />
} ];

/**
 * The application's menu component.
 *
 * @param changeSelectedIndex
 *     The action to change the selected menu index
 * @param className
 *     The component class name
 * @param selectedIndex
 *     The selected menu index
 */
function Menu({ changeSelectedIndex, className, selectedIndex }) {
    /**
     * Defines the menu footer label.
     */
    const footerText = `${process.env.REACT_APP_NAME} v${process.env.REACT_APP_VERSION}`;

    return (<Drawer anchor='left' className={className} id='Menu' variant='permanent'>
        <Logo className='Logo non-selectable' />

        <List>
            {menuItems.map((menuItem, index) => (
                <ListItem button component={Link} key={menuItem.label} onClick={() => changeSelectedIndex(index)} selected={index === selectedIndex}
                          to={menuItem.link}>
                    <ListItemIcon className='Icon'>{menuItem.icon}</ListItemIcon>

                    <ListItemText className='Label' primary={menuItem.label} />
                </ListItem>))}
        </List>

        <Typography className='Footer non-selectable'>{footerText}</Typography>
    </Drawer>);
}

const mapStateToProps = state => ({
    selectedIndex: state.navigation.selectedMenuIndex
});

const mapDispatchToProps = dispatch => bindActionCreators({
    changeSelectedIndex: changeSelectedMenuIndexAction
}, dispatch);

Menu.propTypes = {
    changeSelectedIndex: PropTypes.func.isRequired,
    className: PropTypes.string,
    selectedIndex: PropTypes.number.isRequired
};

Menu.defaultProps = {
    className: ''
};

export default connect(mapStateToProps, mapDispatchToProps)(Menu);
