import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { HashRouter, Redirect, Route, Switch } from 'react-router-dom';

import DateFnsUtils from '@date-io/date-fns';
import { Box, CssBaseline } from '@material-ui/core';
import { MuiPickersUtilsProvider as DatePickerProvider } from '@material-ui/pickers';
import { ThemeProvider } from '@material-ui/styles';

import { AppBar, Menu } from 'components';
import { CarPage, CarsOverviewPage, NotFoundPage } from 'pages';
import { theme } from 'resources';
import { store } from 'store';

import frLocale from 'date-fns/locale/fr';
import 'simplebar';

import './index.scss';
import 'simplebar/dist/simplebar.min.css';

/**
 * Unregister the navigator's service worker registration.
 */
function unregister() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.ready.then(registration => {
            registration.unregister();
        });
    }
}

ReactDOM.render(<ThemeProvider theme={theme}>
    <DatePickerProvider locale={frLocale} utils={DateFnsUtils}>
        <Provider store={store}>
            <HashRouter>
                <CssBaseline />

                <main data-simplebar>
                    <AppBar />

                    <Box className='Page'>
                        <Switch>
                            <Redirect exact from='/' to='/cars' />
                            <Route exact path='/cars' component={CarsOverviewPage} />
                            <Route exact path='/cars/:id' render={props => <CarPage {...props} />} />
                            <Route exact push component={NotFoundPage} />
                        </Switch>
                    </Box>

                    <Menu />
                </main>
            </HashRouter>
        </Provider>
    </DatePickerProvider>
</ThemeProvider>, document.getElementById('root'));

unregister();
