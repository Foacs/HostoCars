import React from 'react';

import logo from '../../resources/logo.svg';

import StyledHomePage from './StyledHomePage';

function HomePage() {
    return (
        <StyledHomePage>
            <header className="header">
                <img src={logo} className="logo" alt="logo" />
                <p>Hello World!</p>
            </header>
        </StyledHomePage>
    );
}

export default HomePage;
