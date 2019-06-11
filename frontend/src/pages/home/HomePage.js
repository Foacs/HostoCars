import React from 'react';

import { Typography } from '@material-ui/core'

import logo from '../../resources/logo.svg';

import StyledHomePage from './StyledHomePage';

function HomePage() {
    return (
        <StyledHomePage>
            <header className="header">
                <img src={logo} className="logo" alt="logo" />
                <Typography>Hello World!</Typography>
            </header>
        </StyledHomePage>
    );
}

export default HomePage;
