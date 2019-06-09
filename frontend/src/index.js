import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import { unregister } from './serviceWorker';

import { HomePage, NotFoundPage } from 'pages';

import './index.scss';

ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={HomePage}/>
            <Route component={NotFoundPage}/>
        </Switch>
    </BrowserRouter>,
    document.getElementById('root')
);

unregister();
