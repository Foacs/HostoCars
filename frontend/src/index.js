import { Box, CssBaseline } from '@material-ui/core';

import { ThemeProvider } from '@material-ui/styles';

import { AppBar, MenuBar } from 'components';
import { createBrowserHistory } from 'history';
import { CarsPage, NotFoundPage } from 'pages';
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Redirect, Route, Router, Switch } from 'react-router-dom';
import { theme } from 'resources';

import 'simplebar';

import 'simplebar/dist/simplebar.min.css';

import { store } from 'store';
import './index.scss';

function unregister() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.ready.then(registration => {
            registration.unregister();
        });
    }
}

ReactDOM.render(<ThemeProvider theme={theme}>
    <Provider store={store}>
        <Router history={createBrowserHistory()}>
            <CssBaseline />

            <main data-simplebar>
                <AppBar />

                <Box className='Page'>
                    <Switch>
                        <Redirect exact from='/' to='/cars' />
                        <Route exact path='/cars' component={CarsPage} />
                        <Route exact push component={NotFoundPage} />
                    </Switch>
                </Box>

                <MenuBar />
            </main>
        </Router>
    </Provider>
</ThemeProvider>, document.getElementById('root'));

unregister();
