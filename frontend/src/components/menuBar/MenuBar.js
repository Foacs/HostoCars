import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';

import StyledMenuBar from './StyledMenuBar';

import { List, ListItem, ListItemIcon, ListItemText, Typography } from '@material-ui/core';
import {
    BuildRounded as TestIcon,
    HomeRounded as HomeIcon,
    SentimentDissatisfiedRounded as NotFoundIcon
} from '@material-ui/icons';

import { changeSelectedMenuIndexAction } from "actions";

import logo from '../../resources/logo.svg';

const menuItems = [
    { label: 'Accueil', link: '/', icon: <HomeIcon /> },
    { label: 'Page introuvable', link: '/404', icon: <NotFoundIcon /> },
    { label: 'Test', link: '/test', icon: <TestIcon /> }
];

function MenuBar({ changeSelectedMenuIndex, selectedMenuIndex }) {
    return (
        <StyledMenuBar anchor="left" variant="permanent">
            <img src={logo} alt="App logo" />

            <List>
                {menuItems.map((menuItem, index) => (
                    <ListItem
                        button
                        component={Link}
                        key={menuItem.label}
                        onClick={() => changeSelectedMenuIndex(index)}
                        selected={index === selectedMenuIndex}
                        to={menuItem.link}>
                        <ListItemIcon>
                            {menuItem.icon}
                        </ListItemIcon>

                        <ListItemText primary={menuItem.label} />
                    </ListItem>
                ))}
            </List>

            <Typography>
                {`${process.env.REACT_APP_NAME} v${process.env.REACT_APP_VERSION}`}
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

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MenuBar);
