import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { HashRouter, Redirect, Route, Switch } from 'react-router-dom';

import DateFnsUtils from '@date-io/date-fns';
import { CssBaseline } from '@material-ui/core';
import { MuiPickersUtilsProvider as DatePickerProvider } from '@material-ui/pickers';
import { ThemeProvider } from '@material-ui/styles';
import { SnackbarProvider } from 'notistack';

import { AppBar, Application, Menu, Notifier } from 'components';
import { ApiPage, CarPage, CarsOverviewPage, InterventionsOverviewPage, NotFoundPage } from 'pages';
import { theme } from 'resources';
import { store } from 'store';

import frLocale from 'date-fns/locale/fr';
import 'simplebar';

import './index.scss';
import 'simplebar/dist/simplebar.min.css';

/**
 * Unregisters the navigator's service worker registration.
 */
const unregister = () => {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.ready.then(registration => {
            registration.unregister();
        });
    }
};

ReactDOM.render(<ThemeProvider theme={theme}>
    <Provider store={store}>
        <SnackbarProvider maxSnack={5}>
            <DatePickerProvider locale={frLocale} utils={DateFnsUtils}>
                <HashRouter>
                    <CssBaseline />

                    <Notifier />

                    <main data-simplebar>
                        <AppBar />

                        <Application>
                            <Switch>
                                <Redirect exact from='/' to='/cars' />
                                <Route exact path='/api' component={ApiPage} />
                                <Route exact path='/cars' component={CarsOverviewPage} />
                                <Route exact path='/cars/:id' render={props => <CarPage {...props} />} />
                                <Route exact path='/interventions' component={InterventionsOverviewPage} />
                                <Route exact push component={NotFoundPage} />
                            </Switch>
                        </Application>

                        <Menu />
                    </main>
                </HashRouter>
            </DatePickerProvider>
        </SnackbarProvider>
    </Provider>
</ThemeProvider>, document.getElementById('root'));

unregister();
