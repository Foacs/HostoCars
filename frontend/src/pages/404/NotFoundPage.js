import React from 'react';

import logo from '../../resources/logo.svg';

import StyledNotFoundPage from './StyledNotFoundPage';

function NotFoundPage() {
    return (
        <StyledNotFoundPage>
            <header className="header">
                <img src={logo} className="logo" alt="logo" />
                <p>404 - Page not found</p>
            </header>
        </StyledNotFoundPage>
    );
}

export default NotFoundPage;
