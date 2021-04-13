import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { AppBar as MaterialAppBar, Fade, IconButton, ListItemIcon, Menu, MenuItem, Toolbar, Typography } from '@material-ui/core';
import { HelpOutlineRounded as AboutIcon, MenuRounded as MenuIcon } from '@material-ui/icons';

import { Breadcrumbs } from 'components';
import { AboutModal } from 'modals';

import './AppBar.scss';

/**
 * The application's bar component.
 *
 * @param {string} [className = '']
 *     The component class name
 * @param {array} menuItems
 *     The menu items
 *
 * @constructor
 */
class AppBar extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = {
            anchorEl: null,
            isAboutModelOpen: false
        };

        // Binds the local methods
        this.onAboutModalClose = this.onAboutModalClose.bind(this);
        this.onAboutModalOpen = this.onAboutModalOpen.bind(this);
        this.onCloseMenu = this.onCloseMenu.bind(this);
        this.onOpenMenu = this.onOpenMenu.bind(this);
    }

    /**
     * Handles the about modal close action.
     */
    onAboutModalClose() {
        this.setState({ isAboutModelOpen: false });
    }

    /**
     * Handles the about modal open action.
     */
    onAboutModalOpen() {
        this.setState({ isAboutModelOpen: true });
        this.onCloseMenu();
    }

    /**
     * Handles the menu close action.
     */
    onCloseMenu() {
        this.setState({ anchorEl: null });
    }

    /**
     * Handles the menu open action.
     */
    onOpenMenu(event) {
        this.setState({ anchorEl: event.currentTarget });
    }

    /**
     * Render method.
     */
    render() {
        const {
            anchorEl,
            isAboutModelOpen
        } = this.state;
        const {
            className,
            menuItems
        } = this.props;

        return (<>
            <MaterialAppBar className={className} id='AppBar' position='fixed'>
                <Toolbar>
                    <Breadcrumbs />

                    <IconButton className="MenuButton" onClick={this.onOpenMenu}>
                        <MenuIcon className="MenuIcon" />
                    </IconButton>

                    <Menu anchorEl={anchorEl} id='AppBarMenu' keepMounted onClose={this.onCloseMenu} open={Boolean(anchorEl)}
                          TransitionComponent={Fade}>
                        {menuItems.map(menuItem => (<MenuItem key={menuItem.label} onClick={() => {
                            menuItem.onClick();
                            this.onCloseMenu();
                        }}>
                            <ListItemIcon className='MenuItemIcon'>{menuItem.icon}</ListItemIcon>
                            <Typography className='MenuItemLabel'>{menuItem.label}</Typography>
                        </MenuItem>))}
                        <MenuItem onClick={this.onAboutModalOpen}>
                            <ListItemIcon className='MenuItemIcon'><AboutIcon /></ListItemIcon>
                            <Typography className='MenuItemLabel'>À propos</Typography>
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </MaterialAppBar>

            <AboutModal onClose={this.onAboutModalClose} open={isAboutModelOpen} />
        </>);
    }
}

const mapStateToProps = (state) => ({
    menuItems: state.navigation.menuItems
});

AppBar.propTypes = {
    className: PropTypes.string,
    menuItems: PropTypes.arrayOf(PropTypes.shape({
        icon: PropTypes.node.isRequired,
        label: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired
    })).isRequired,
};

AppBar.defaultProps = {
    className: ''
};

export default connect(mapStateToProps, null)(AppBar);
