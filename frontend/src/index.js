import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Route, Router, Switch } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import { ThemeProvider } from '@material-ui/styles';

import { unregister } from './serviceWorker';
import { store } from 'store';
import { theme } from 'resources';

import { HomePage, NotFoundPage, TestPage } from 'pages';

import './index.scss';

ReactDOM.render(
    <ThemeProvider theme={theme}>
        <Provider store={store}>
            <Router history={createBrowserHistory()}>
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/test" component={TestPage} />
                    <Route component={NotFoundPage} />
                </Switch>
            </Router>
        </Provider>
    </ThemeProvider>,
    document.getElementById('root')
);

unregister();
