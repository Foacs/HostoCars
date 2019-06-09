import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import { unregister } from './serviceWorker';
import { store } from 'store';

import { HomePage, NotFoundPage, TestPage } from 'pages';

import './index.scss';

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={HomePage}/>
                <Route exact path="/test" component={TestPage}/>
                <Route component={NotFoundPage}/>
            </Switch>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);

unregister();
