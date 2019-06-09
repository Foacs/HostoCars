import React from 'react';

import logo from '../../resources/logo.svg';

import './HomePage.css';

function HomePage() {
    return (
        <div className="HomePage">
            <header className="HomePage__header">
                <img src={logo} className="HomePage__header__logo" alt="logo" />
                <p>Hello World!</p>
            </header>
        </div>
    );
}

export default HomePage;
