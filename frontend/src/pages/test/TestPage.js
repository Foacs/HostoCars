import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import logo from '../../resources/logo.svg';

import { logUserInAction, logUserOutAction } from 'actions';

import StyledTestPage from './StyledTestPage';

function TestPage({ isUserLogged, logUserIn, logUserOut }) {
    return (
        <StyledTestPage>
            <header className="header">
                <img src={logo} className="logo" alt="logo" />
                <p>Test</p>
                <button onClick={isUserLogged ? logUserOut : logUserIn}>{isUserLogged ? 'Log out' : 'Log in'}</button>
                <span>{isUserLogged ? 'Logged' : 'Not logged'}</span>
            </header>
        </StyledTestPage>
    );
}

const mapStateToProps = state => ({
    isUserLogged: state.isUserLogged,
});

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            logUserIn: logUserInAction,
            logUserOut: logUserOutAction,
        },
        dispatch,
    );

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(TestPage);
