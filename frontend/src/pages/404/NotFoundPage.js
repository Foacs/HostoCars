import React from 'react';

import logo from '../../resources/logo.svg';

import './NotFoundPage.css';

function NotFoundPage() {
    return (
        <div className="NotFoundPage">
            <header className="NotFoundPage__header">
                <img src={logo} className="NotFoundPage__header__logo" alt="logo" />
                <p>404 - Page not found</p>
            </header>
        </div>
    );
}

export default NotFoundPage;
