import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Drawer, List, ListItem, ListItemIcon, ListItemText } from '@material-ui/core';

import { updateSelectedMenuIndexAction } from 'actions';
import { Logo, menuItems } from 'resources';

import './Menu.scss';

/**
 * The application menu component.
 *
 * @param {func} updateSelectedIndex
 *     The {@link updateSelectedMenuIndexAction} action
 * @param {string} [className = '']
 *     The component class name
 * @param {number} selectedIndex
 *     The selected menu index
 *
 * @constructor
 */
function Menu({
    updateSelectedIndex,
    className,
    selectedIndex
}) {
    return (<Drawer anchor='left' className={className} id='Menu' variant='permanent'>
        <Logo className='Logo non-selectable' />

        <List>
            {menuItems.map((menuItem, index) => (
                    <ListItem button component={Link} key={menuItem.label} onClick={() => updateSelectedIndex(index)}
                              selected={selectedIndex === index} to={menuItem.link} replace>
                        <ListItemIcon className='Icon'>{menuItem.icon}</ListItemIcon>

                        <ListItemText className='Label' primary={menuItem.label} />
                    </ListItem>))}
        </List>
    </Drawer>);
}

const mapStateToProps = (state) => ({
    selectedIndex: state.navigation.selectedMenuIndex
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    updateSelectedIndex: updateSelectedMenuIndexAction
}, dispatch);

Menu.propTypes = {
    updateSelectedIndex: PropTypes.func.isRequired,
    className: PropTypes.string,
    selectedIndex: PropTypes.number.isRequired
};

Menu.defaultProps = {
    className: ''
};

export default connect(mapStateToProps, mapDispatchToProps)(Menu);
