import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Router, Route, Switch } from 'react-router-dom';
import { createBrowserHistory } from 'history';

import { unregister } from './serviceWorker';
import { store } from 'store';

import { HomePage, NotFoundPage, TestPage } from 'pages';

import './index.scss';

ReactDOM.render(
    <Provider store={store}>
        <Router history={createBrowserHistory()}>
            <Switch>
                <Route exact path="/" component={HomePage}/>
                <Route path="/test" component={TestPage}/>
                <Route component={NotFoundPage}/>
            </Switch>
        </Router>
    </Provider>,
    document.getElementById('root')
);

unregister();
