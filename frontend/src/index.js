import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Route, Router, Switch } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import { ThemeProvider } from '@material-ui/styles';

import { store } from 'store';
import { theme } from 'resources';

import { CssBaseline } from '@material-ui/core';

import { AppBar, MenuBar } from 'components';
import { HomePage, NotFoundPage, TestPage } from 'pages';

import './index.scss';

function unregister() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.ready.then(registration => {
            registration.unregister();
        });
    }
}

ReactDOM.render(
    <ThemeProvider theme={theme}>
        <Provider store={store}>
            <Router history={createBrowserHistory()}>
                <CssBaseline />

                <AppBar />

                <main>
                    <Switch>
                        <Route exact path="/" component={HomePage} />
                        <Route exact path="/test" component={TestPage} />
                        <Route component={NotFoundPage} />
                    </Switch>
                </main>

                <MenuBar />
            </Router>
        </Provider>
    </ThemeProvider>,
    document.getElementById('root')
);

unregister();
